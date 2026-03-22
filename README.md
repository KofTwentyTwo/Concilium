# Concilium

Master-agent orchestration platform for multi-repository software delivery.

Concilium coordinates planning, issue-backed work, architecture awareness, and delivery convergence across multiple repos using persistent AI agents.

## Quick Start

### Prerequisites

- Java 21
- Node.js 22+
- Docker and Docker Compose
- QQQ 0.40.0-SNAPSHOT installed to mavenLocal (`mvn install` from qqq repo)

### Local Development

```bash
# Start everything (Postgres, MinIO, backend, frontend)
docker/start-local-dev.sh

# Or start services only, run app from IDE
docker/start-local-dev.sh --no-frontend
```

### Services

| Service | URL |
|---|---|
| Backend API | http://localhost:8000 |
| Admin Dashboard | http://localhost:8000 |
| Frontend | http://localhost:3000 |
| PostgreSQL | localhost:5456 |
| MinIO Console | http://localhost:9031 |

### Build

```bash
# Build backend
./gradlew build

# Build frontend
cd frontend && npm run build

# Build shadow JAR
./gradlew :concilium-server:shadowJar
```

## Architecture

See `docs/superpowers/specs/2026-03-22-concilium-bootstrap-design.md` for the full design spec.

## License

MIT
