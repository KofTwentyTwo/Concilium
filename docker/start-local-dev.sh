#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

cd "$PROJECT_ROOT"

FRESH=false
NO_FRONTEND=false
while [[ $# -gt 0 ]]; do
    case $1 in
        --fresh) FRESH=true; shift ;;
        --no-frontend) NO_FRONTEND=true; shift ;;
        *) echo "Unknown option: $1"; echo "Usage: $0 [--fresh] [--no-frontend]"; exit 1 ;;
    esac
done

echo "=== Concilium - Local Development ==="

if [ "$FRESH" = true ]; then
    echo "Removing all volumes (clean database)..."
    docker compose -f docker/compose.yml down -v 2>/dev/null || true
else
    docker compose -f docker/compose.yml down 2>/dev/null || true
fi

echo "Building application..."
./gradlew :concilium-server:shadowJar -q

echo "Starting docker services..."
docker compose -f docker/compose.yml up -d

echo "Waiting for PostgreSQL..."
until docker exec concilium-postgres pg_isready -U devuser -d concilium > /dev/null 2>&1; do
    sleep 2
done
echo "PostgreSQL ready"

# Run Liquibase migrations (empty changelog for now, but wired for future use)
PG_DRIVER="$PROJECT_ROOT/docker/.cache/postgresql-42.7.2.jar"
if [ ! -f "$PG_DRIVER" ]; then
    mkdir -p "$PROJECT_ROOT/docker/.cache"
    curl -sSL -o "$PG_DRIVER" https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.2/postgresql-42.7.2.jar
fi
DOCKER_NETWORK=$(docker inspect concilium-postgres --format '{{range $k,$v := .NetworkSettings.Networks}}{{$k}}{{end}}' 2>/dev/null)
docker run --rm --network "$DOCKER_NETWORK" \
    -v "$PROJECT_ROOT/concilium-core/src/main/resources/db:/liquibase/db:ro" \
    -v "$PG_DRIVER:/liquibase/lib/postgresql.jar:ro" \
    --entrypoint bash liquibase/liquibase:4.30.0 -c \
    'LB="--changelog-file=db/liquibase/changelog.yaml --url=jdbc:postgresql://concilium-postgres:5432/concilium --username=devuser --password=devpass --search-path=/liquibase" && liquibase $LB update' \
    && echo "Migrations complete" || echo "Migrations skipped (empty changelog)"

echo "Waiting for MinIO..."
until curl -sf http://localhost:9030/minio/health/live > /dev/null 2>&1; do
    sleep 2
done
echo "MinIO ready"

docker exec concilium-minio mc alias set local http://localhost:9000 minioadmin minioadmin 2>/dev/null
docker exec concilium-minio mc mb local/concilium-artifacts --ignore-existing 2>/dev/null || true
echo "MinIO buckets ready"

echo ""
echo "=== Services Ready ==="
echo "PostgreSQL:  localhost:5456 (devuser/devpass)"
echo "MinIO API:   http://localhost:9030"
echo "MinIO UI:    http://localhost:9031 (minioadmin/minioadmin)"
echo "Backend:     http://localhost:8000"
echo ""

export RDBMS_VENDOR=postgresql
export RDBMS_HOSTNAME=localhost
export RDBMS_PORT=5456
export RDBMS_DATABASE_NAME=concilium
export RDBMS_USERNAME=devuser
export RDBMS_PASSWORD=devpass
export S3_ENDPOINT=http://localhost:9030
export S3_BUCKET_NAME=concilium-artifacts
export S3_ACCESS_KEY=minioadmin
export S3_SECRET_KEY=minioadmin
export S3_REGION=us-east-1
export AUTH_MODE=dev_local

if [ "$NO_FRONTEND" = false ] && [ -d "$PROJECT_ROOT/frontend" ] && [ -f "$PROJECT_ROOT/frontend/package.json" ]; then
    echo "Starting frontend dev server..."
    (cd "$PROJECT_ROOT/frontend" && npm run dev &)
fi

echo "Starting backend..."
java -jar concilium-server/build/libs/concilium.jar
