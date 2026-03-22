# Project Foundation Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Establish the Gradle multi-module build system, Docker dev stack, CI/CD pipeline, and repo scaffolding so that subsequent plans can build on a working, compilable project.

**Architecture:** Gradle Kotlin DSL multi-module project consuming QQQ 0.40.0-SNAPSHOT from mavenLocal. Five backend modules (core, orchestration, integrations, memory, server) plus a frontend/ directory for Next.js. Docker Compose provides Postgres 17 + MinIO for local dev. Munitor orb handles CI/CD.

**Tech Stack:** Java 21, Gradle (Kotlin DSL), QQQ 0.40.0-SNAPSHOT, PostgreSQL 17, Liquibase 4.x, MinIO, CircleCI + Munitor, Next.js (latest stable)

**Spec:** `docs/superpowers/specs/2026-03-22-concilium-bootstrap-design.md`

**Depends on:** Nothing (this is the first plan)

**Produces:** A compilable, testable, runnable project skeleton with empty modules, Docker dev stack, and CI/CD config.

---

## File Structure

### Root Build Files

| File | Responsibility |
|---|---|
| `settings.gradle.kts` | Project name, module includes, version catalog |
| `build.gradle.kts` | Root build config: Java 21 toolchain, shared plugin config |
| `gradle.properties` | Group, version, QQQ version, Java version |
| `gradle/libs.versions.toml` | Central version catalog for all dependencies |
| `.editorconfig` | Editor settings (3-space indent for Java, 2-space for YAML) |

### Module Build Files

| File | Responsibility |
|---|---|
| `concilium-core/build.gradle.kts` | Core module deps: QQQ BOM, qqq-backend-core, qqq-backend-module-postgres, JPA, Liquibase |
| `concilium-orchestration/build.gradle.kts` | Orchestration deps: depends on core |
| `concilium-integrations/build.gradle.kts` | Integration deps: depends on core, HTTP client libs |
| `concilium-memory/build.gradle.kts` | Memory deps: depends on core, qqq-backend-module-filesystem |
| `concilium-server/build.gradle.kts` | Server deps: depends on all modules, qqq-middleware-javalin, shadow JAR |

### Module Source Placeholders

| File | Responsibility |
|---|---|
| `concilium-core/src/main/java/com/kof22/concilium/package-info.java` | Package marker |
| `concilium-core/src/test/java/com/kof22/concilium/CoreSmokeTest.java` | Verifies QQQ core is on classpath |
| `concilium-orchestration/src/main/java/com/kof22/concilium/orchestration/package-info.java` | Package marker |
| `concilium-integrations/src/main/java/com/kof22/concilium/integrations/package-info.java` | Package marker |
| `concilium-memory/src/main/java/com/kof22/concilium/memory/package-info.java` | Package marker |
| `concilium-server/src/main/java/com/kof22/concilium/metadata/ConciliumMetaDataProvider.java` | Minimal AbstractQQQApplication subclass |
| `concilium-server/src/main/java/com/kof22/concilium/ConciliumServer.java` | Main class, starts QApplicationJavalinServer |
| `concilium-server/src/test/java/com/kof22/concilium/ServerSmokeTest.java` | Verifies QInstance creation |
| `concilium-server/src/main/resources/log4j2.xml` | Logging config |

### Docker / Dev Stack

| File | Responsibility |
|---|---|
| `docker/compose.yml` | Postgres 17 + MinIO containers |
| `docker/init-multi-db.sh` | Multi-database init script for Postgres |
| `docker/start-local-dev.sh` | One-command bootstrap: containers + migrations + backend + frontend |
| `docker/stop-local-dev.sh` | Teardown script |
| `docker/Dockerfile.migrations` | Liquibase migrations container |
| `Dockerfile` | Production backend container (distroless/java21) |

### CI/CD

| File | Responsibility |
|---|---|
| `.circleci/config.yml` | Minimal bootstrap delegating to Munitor |
| `.munitor.yml` | Pipeline definition for Concilium |

### Config / Meta

| File | Responsibility |
|---|---|
| `.env.example` | Environment variable template |
| `.gitignore` | Updated for Gradle + Node + IntelliJ |
| `.editorconfig` | Editor formatting rules |
| `README.md` | Project overview, setup instructions |

### Frontend Placeholder

| File | Responsibility |
|---|---|
| `frontend/package.json` | Next.js project definition |
| `frontend/next.config.mjs` | API proxy rewrites to backend |
| `frontend/tsconfig.json` | TypeScript config |
| `frontend/tailwind.config.ts` | Tailwind config |
| `frontend/postcss.config.cjs` | PostCSS config for Tailwind |
| `frontend/src/app/layout.tsx` | Root layout |
| `frontend/src/app/page.tsx` | Placeholder landing page |
| `frontend/src/styles/globals.css` | Tailwind base styles |

### Liquibase Placeholder

| File | Responsibility |
|---|---|
| `concilium-core/src/main/resources/db/liquibase/changelog.yaml` | Empty changelog manifest |
| `concilium-core/src/main/resources/db/liquibase/liquibase.properties` | Connection config template |

---

## Task 1: Gradle Wrapper and Root Build Files

**Files:**
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts`
- Create: `gradle.properties`
- Create: `gradle/libs.versions.toml`

- [ ] **Step 1: Generate Gradle wrapper**

```bash
cd /Users/james.maes/Git.Local/Kof22/Concilium
gradle wrapper --gradle-version 8.13
```

This creates `gradlew`, `gradlew.bat`, and `gradle/wrapper/` directory.

- [ ] **Step 2: Create `gradle.properties`**

```properties
group=com.kof22.concilium
version=0.1.0-SNAPSHOT
qqqVersion=0.40.0-SNAPSHOT
javaVersion=21
```

- [ ] **Step 3: Create `gradle/libs.versions.toml`**

```toml
[versions]
qqq = "0.40.0-SNAPSHOT"
java = "21"
postgres = "42.7.2"
liquibase = "4.19.0"
log4j = "2.25.3"
junit = "5.11.4"
assertj = "3.27.3"
h2 = "2.2.220"
jackson = "2.18.3"
hibernate = "6.4.4.Final"
jakartaPersistence = "3.1.0"

[libraries]
qqq-bom = { module = "com.kingsrook.qqq:qqq-bom-pom", version.ref = "qqq" }
qqq-backend-core = { module = "com.kingsrook.qqq:qqq-backend-core", version.ref = "qqq" }
qqq-backend-module-rdbms = { module = "com.kingsrook.qqq:qqq-backend-module-rdbms", version.ref = "qqq" }
qqq-backend-module-postgres = { module = "com.kingsrook.qqq:qqq-backend-module-postgres", version.ref = "qqq" }
qqq-backend-module-filesystem = { module = "com.kingsrook.qqq:qqq-backend-module-filesystem", version.ref = "qqq" }
qqq-middleware-javalin = { module = "com.kingsrook.qqq:qqq-middleware-javalin", version.ref = "qqq" }
qqq-middleware-picocli = { module = "com.kingsrook.qqq:qqq-middleware-picocli", version.ref = "qqq" }
qqq-middleware-health = { module = "com.kingsrook.qqq:qqq-middleware-health", version.ref = "qqq" }
qqq-frontend-material-dashboard = { module = "com.kingsrook.qqq:qqq-frontend-material-dashboard", version.ref = "qqq" }
jakarta-persistence-api = { module = "jakarta.persistence:jakarta.persistence-api", version.ref = "jakartaPersistence" }
hibernate-core = { module = "org.hibernate.orm:hibernate-core", version.ref = "hibernate" }
postgres-driver = { module = "org.postgresql:postgresql", version.ref = "postgres" }
liquibase-core = { module = "org.liquibase:liquibase-core", version.ref = "liquibase" }
log4j-api = { module = "org.apache.logging.log4j:log4j-api", version.ref = "log4j" }
log4j-core = { module = "org.apache.logging.log4j:log4j-core", version.ref = "log4j" }
slf4j-simple = { module = "org.slf4j:slf4j-simple", version = "2.0.6" }
junit-jupiter = { module = "org.junit.jupiter:junit-jupiter", version.ref = "junit" }
assertj-core = { module = "org.assertj:assertj-core", version.ref = "assertj" }
h2 = { module = "com.h2database:h2", version.ref = "h2" }
jackson-databind = { module = "com.fasterxml.jackson.core:jackson-databind", version.ref = "jackson" }
jackson-datatype-jsr310 = { module = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310", version.ref = "jackson" }

[plugins]
shadow = { id = "com.gradleup.shadow", version = "9.0.0-beta12" }
```

- [ ] **Step 4: Create `settings.gradle.kts`**

```kotlin
rootProject.name = "Concilium"

include("concilium-core")
include("concilium-orchestration")
include("concilium-integrations")
include("concilium-memory")
include("concilium-server")
```

- [ ] **Step 5: Create root `build.gradle.kts`**

```kotlin
plugins {
   java
   jacoco
}

allprojects {
   repositories {
      mavenLocal()
      mavenCentral()
   }
}

subprojects {
   apply(plugin = "java")
   apply(plugin = "jacoco")

   java {
      toolchain {
         languageVersion.set(JavaLanguageVersion.of(21))
      }
   }

   tasks.withType<JavaCompile> {
      options.encoding = "UTF-8"
      options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
   }

   tasks.withType<Test> {
      useJUnitPlatform()
   }

   tasks.jacocoTestReport {
      reports {
         xml.required.set(true)
         html.required.set(true)
      }
   }

   dependencies {
      testImplementation(libs.junit.jupiter)
      testImplementation(libs.assertj.core)
   }
}
```

- [ ] **Step 6: Verify wrapper works**

Run: `./gradlew --version`
Expected: Gradle 8.13 with JVM 21

- [ ] **Step 7: Commit**

```bash
git add settings.gradle.kts build.gradle.kts gradle.properties gradle/ gradlew gradlew.bat
git commit -m "feat: add Gradle wrapper and root build configuration"
```

---

## Task 2: Module Build Files

**Files:**
- Create: `concilium-core/build.gradle.kts`
- Create: `concilium-orchestration/build.gradle.kts`
- Create: `concilium-integrations/build.gradle.kts`
- Create: `concilium-memory/build.gradle.kts`
- Create: `concilium-server/build.gradle.kts`

- [ ] **Step 1: Create `concilium-core/build.gradle.kts`**

```kotlin
dependencies {
   api(platform(libs.qqq.bom))
   api(libs.qqq.backend.core)
   api(libs.qqq.backend.module.rdbms)
   api(libs.qqq.backend.module.postgres)
   api(libs.qqq.backend.module.filesystem)

   implementation(libs.jakarta.persistence.api)
   implementation(libs.hibernate.core)
   implementation(libs.postgres.driver)
   implementation(libs.liquibase.core)
   implementation(libs.log4j.api)
   implementation(libs.log4j.core)
   implementation(libs.jackson.databind)
   implementation(libs.jackson.datatype.jsr310)

   testImplementation(libs.h2)
}
```

- [ ] **Step 2: Create `concilium-orchestration/build.gradle.kts`**

```kotlin
dependencies {
   implementation(project(":concilium-core"))

   implementation(libs.log4j.api)
   implementation(libs.log4j.core)
}
```

- [ ] **Step 3: Create `concilium-integrations/build.gradle.kts`**

```kotlin
dependencies {
   implementation(project(":concilium-core"))

   implementation(libs.log4j.api)
   implementation(libs.log4j.core)
   implementation(libs.jackson.databind)
}
```

- [ ] **Step 4: Create `concilium-memory/build.gradle.kts`**

```kotlin
dependencies {
   implementation(project(":concilium-core"))

   implementation(libs.qqq.backend.module.filesystem)
   implementation(libs.log4j.api)
   implementation(libs.log4j.core)
}
```

- [ ] **Step 5: Create `concilium-server/build.gradle.kts`**

```kotlin
plugins {
   alias(libs.plugins.shadow)
}

dependencies {
   implementation(project(":concilium-core"))
   implementation(project(":concilium-orchestration"))
   implementation(project(":concilium-integrations"))
   implementation(project(":concilium-memory"))

   implementation(libs.qqq.middleware.javalin)
   implementation(libs.qqq.middleware.picocli)
   implementation(libs.qqq.middleware.health)
   implementation(libs.qqq.frontend.material.dashboard)
   implementation(libs.slf4j.simple)
   implementation(libs.log4j.api)
   implementation(libs.log4j.core)

   testImplementation(libs.h2)
}

tasks.shadowJar {
   archiveBaseName.set("concilium")
   archiveClassifier.set("")
   archiveVersion.set("")
   manifest {
      attributes["Main-Class"] = "com.kof22.concilium.ConciliumServer"
   }
   mergeServiceFiles()
}
```

- [ ] **Step 6: Verify project resolves**

Run: `./gradlew projects`
Expected: Lists all 5 subprojects under root project 'Concilium'

- [ ] **Step 7: Commit**

```bash
git add concilium-core/build.gradle.kts concilium-orchestration/build.gradle.kts concilium-integrations/build.gradle.kts concilium-memory/build.gradle.kts concilium-server/build.gradle.kts
git commit -m "feat: add module build files for all five subprojects"
```

---

## Task 3: Module Source Placeholders and Smoke Tests

**Files:**
- Create: `concilium-core/src/main/java/com/kof22/concilium/package-info.java`
- Create: `concilium-core/src/test/java/com/kof22/concilium/CoreSmokeTest.java`
- Create: `concilium-orchestration/src/main/java/com/kof22/concilium/orchestration/package-info.java`
- Create: `concilium-integrations/src/main/java/com/kof22/concilium/integrations/package-info.java`
- Create: `concilium-memory/src/main/java/com/kof22/concilium/memory/package-info.java`

- [ ] **Step 1: Create package-info files for all modules**

`concilium-core/src/main/java/com/kof22/concilium/package-info.java`:
```java
/*******************************************************************************
 ** Root package for Concilium core domain model and metadata.
 *******************************************************************************/
package com.kof22.concilium;
```

`concilium-orchestration/src/main/java/com/kof22/concilium/orchestration/package-info.java`:
```java
/*******************************************************************************
 ** Orchestration engine: workflow management, agent lifecycle, governance.
 *******************************************************************************/
package com.kof22.concilium.orchestration;
```

`concilium-integrations/src/main/java/com/kof22/concilium/integrations/package-info.java`:
```java
/*******************************************************************************
 ** Integration adapters: GitHub, CircleCI, Claude Code, Claude API.
 *******************************************************************************/
package com.kof22.concilium.integrations;
```

`concilium-memory/src/main/java/com/kof22/concilium/memory/package-info.java`:
```java
/*******************************************************************************
 ** Memory services: persistence, consolidation, artifact storage.
 *******************************************************************************/
package com.kof22.concilium.memory;
```

- [ ] **Step 2: Write CoreSmokeTest**

`concilium-core/src/test/java/com/kof22/concilium/CoreSmokeTest.java`:
```java
package com.kof22.concilium;


import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Smoke test verifying QQQ core is on the classpath and functional.
 *******************************************************************************/
class CoreSmokeTest
{


   /*******************************************************************************
    ** Verify QInstance can be instantiated from qqq-backend-core.
    *******************************************************************************/
   @Test
   void testQInstanceAvailable()
   {
      QInstance qInstance = new QInstance();
      assertThat(qInstance).isNotNull();
      assertThat(qInstance.getTables()).isEmpty();
   }
}
```

- [ ] **Step 3: Run smoke test**

Run: `./gradlew :concilium-core:test`
Expected: BUILD SUCCESSFUL, 1 test passed. This validates QQQ SNAPSHOT is available via mavenLocal.

- [ ] **Step 4: Commit**

```bash
git add concilium-core/src concilium-orchestration/src concilium-integrations/src concilium-memory/src
git commit -m "feat: add module source placeholders and QQQ smoke test"
```

---

## Task 4: Minimal ConciliumServer and MetaDataProvider

**Files:**
- Create: `concilium-server/src/main/java/com/kof22/concilium/metadata/ConciliumMetaDataProvider.java`
- Create: `concilium-server/src/main/java/com/kof22/concilium/ConciliumServer.java`
- Create: `concilium-server/src/main/resources/log4j2.xml`
- Create: `concilium-server/src/test/java/com/kof22/concilium/ServerSmokeTest.java`

- [ ] **Step 1: Write ServerSmokeTest**

`concilium-server/src/test/java/com/kof22/concilium/ServerSmokeTest.java`:
```java
package com.kof22.concilium;


import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kof22.concilium.metadata.ConciliumMetaDataProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Smoke test verifying the ConciliumMetaDataProvider can produce a QInstance.
 *******************************************************************************/
class ServerSmokeTest
{


   /*******************************************************************************
    ** Verify QInstance is produced without exceptions.
    *******************************************************************************/
   @Test
   void testMetaDataProviderProducesInstance() throws Exception
   {
      ConciliumMetaDataProvider provider = new ConciliumMetaDataProvider();
      QInstance qInstance = provider.defineValidatedQInstance();
      assertThat(qInstance).isNotNull();
   }
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `./gradlew :concilium-server:test`
Expected: FAIL (ConciliumMetaDataProvider does not exist yet)

- [ ] **Step 3: Create ConciliumMetaDataProvider**

`concilium-server/src/main/java/com/kof22/concilium/metadata/ConciliumMetaDataProvider.java`:
```java
package com.kof22.concilium.metadata;


import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.instances.AbstractQQQApplication;
import com.kingsrook.qqq.backend.core.model.metadata.MetaDataProducerHelper;
import com.kingsrook.qqq.backend.core.model.metadata.QAuthenticationType;
import com.kingsrook.qqq.backend.core.model.metadata.QBackendMetaData;
import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kingsrook.qqq.backend.core.model.metadata.authentication.QAuthenticationMetaData;
import com.kingsrook.qqq.backend.core.modules.backend.implementations.memory.MemoryBackendModule;


/*******************************************************************************
 ** MetaData provider for the Concilium application.
 ** Extends AbstractQQQApplication to define the full QInstance.
 *******************************************************************************/
public class ConciliumMetaDataProvider extends AbstractQQQApplication
{
   public static final String MEMORY_BACKEND_NAME = "memory";



   /*******************************************************************************
    ** Define the QInstance for Concilium.
    *******************************************************************************/
   @Override
   public QInstance defineQInstance() throws QException
   {
      QInstance qInstance = new QInstance();

      qInstance.addBackend(defineMemoryBackend());
      qInstance.setAuthentication(defineAuthentication());

      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.model");
      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.metadata");

      return qInstance;
   }



   /*******************************************************************************
    ** Define the in-memory backend (for dev/test, replaced by Postgres in Plan 2).
    *******************************************************************************/
   private QBackendMetaData defineMemoryBackend()
   {
      return new QBackendMetaData()
         .withName(MEMORY_BACKEND_NAME)
         .withBackendType(MemoryBackendModule.class);
   }



   /*******************************************************************************
    ** Define mock authentication (for dev, replaced by OAuth2 in later phase).
    *******************************************************************************/
   private QAuthenticationMetaData defineAuthentication()
   {
      return new QAuthenticationMetaData()
         .withName("mock")
         .withType(QAuthenticationType.MOCK);
   }
}
```

- [ ] **Step 4: Create ConciliumServer**

`concilium-server/src/main/java/com/kof22/concilium/ConciliumServer.java`:
```java
package com.kof22.concilium;


import com.kingsrook.qqq.backend.core.logging.QLogger;
import com.kingsrook.qqq.middleware.javalin.QApplicationJavalinServer;
import com.kof22.concilium.metadata.ConciliumMetaDataProvider;

import static com.kingsrook.qqq.backend.core.logging.LogPair.logPair;


/*******************************************************************************
 ** Main entry point for the Concilium backend server.
 *******************************************************************************/
public class ConciliumServer
{
   private static final QLogger LOG = QLogger.getLogger(ConciliumServer.class);

   private static final Integer DEFAULT_PORT = 8000;



   /*******************************************************************************
    ** Main method.
    *******************************************************************************/
   public static void main(String[] args)
   {
      new ConciliumServer().start();
   }



   /*******************************************************************************
    ** Start the Javalin server with QQQ.
    *******************************************************************************/
   public void start()
   {
      try
      {
         Integer port = getPort();

         QApplicationJavalinServer javalinServer = new QApplicationJavalinServer(new ConciliumMetaDataProvider())
            .withServeFrontendMaterialDashboard(true)
            .withPort(port);

         javalinServer.start();

         LOG.info("Concilium server started", logPair("port", port));
      }
      catch(Exception e)
      {
         LOG.error("Failed to start Concilium server", e);
         System.exit(1);
      }
   }



   /*******************************************************************************
    ** Get port from environment or use default.
    *******************************************************************************/
   private Integer getPort()
   {
      String portEnv = System.getenv("SERVER_PORT");
      if(portEnv != null)
      {
         try
         {
            return Integer.valueOf(portEnv);
         }
         catch(NumberFormatException e)
         {
            LOG.warn("Invalid SERVER_PORT value, using default", logPair("invalidValue", portEnv), logPair("defaultPort", DEFAULT_PORT));
         }
      }
      return DEFAULT_PORT;
   }
}
```

- [ ] **Step 5: Create log4j2.xml**

`concilium-server/src/main/resources/log4j2.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
   <Appenders>
      <Console name="Console" target="SYSTEM_OUT">
         <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
      </Console>
   </Appenders>
   <Loggers>
      <Logger name="com.kof22.concilium" level="${env:LOG_LEVEL:-INFO}" additivity="false">
         <AppenderRef ref="Console"/>
      </Logger>
      <Logger name="com.kingsrook.qqq" level="${env:QQQ_LOG_LEVEL:-INFO}" additivity="false">
         <AppenderRef ref="Console"/>
      </Logger>
      <Root level="WARN">
         <AppenderRef ref="Console"/>
      </Root>
   </Loggers>
</Configuration>
```

- [ ] **Step 6: Run smoke test**

Run: `./gradlew :concilium-server:test`
Expected: BUILD SUCCESSFUL, 1 test passed

- [ ] **Step 7: Build shadow JAR and verify it runs**

Run: `./gradlew :concilium-server:shadowJar`
Expected: `concilium-server/build/libs/concilium.jar` created

Run: `java -jar concilium-server/build/libs/concilium.jar &`
Wait 3 seconds, then: `curl -s http://localhost:8000/ | head -5`
Expected: HTML response from Material Dashboard (or a redirect)
Then: kill the background process

- [ ] **Step 8: Commit**

```bash
git add concilium-server/src
git commit -m "feat: add ConciliumServer and ConciliumMetaDataProvider skeleton"
```

---

## Task 5: Spike -- Multi-Module Metadata Discovery

**Files:**
- Create: `concilium-core/src/main/java/com/kof22/concilium/model/package-info.java`
- Modify: `concilium-server/src/test/java/com/kof22/concilium/ServerSmokeTest.java`

This spike validates that `MetaDataProducerHelper.processAllMetaDataProducersInPackage()` discovers `@QMetaDataProducingEntity` classes in `concilium-core` when called from `concilium-server`. This is critical because QQQ reference apps are single-module, but Concilium is multi-module.

- [ ] **Step 1: Create model package marker**

`concilium-core/src/main/java/com/kof22/concilium/model/package-info.java`:
```java
/*******************************************************************************
 ** Root package for Concilium domain model entities.
 *******************************************************************************/
package com.kof22.concilium.model;
```

- [ ] **Step 2: Verify smoke test still passes**

Run: `./gradlew :concilium-server:test`
Expected: BUILD SUCCESSFUL. The `processAllMetaDataProducersInPackage` call scans `com.kof22.concilium.model` from `concilium-core` and finds nothing (no entities yet), but does not throw. This confirms cross-module package scanning works.

If this fails with a classpath discovery error, the fallback is to move all entity classes into `concilium-server` with package-level separation instead of module separation.

- [ ] **Step 3: Commit**

```bash
git add concilium-core/src/main/java/com/kof22/concilium/model/
git commit -m "spike: validate multi-module metadata discovery"
```

---

## Task 6: Docker Dev Stack

**Files:**
- Create: `docker/compose.yml`
- Create: `docker/init-multi-db.sh`
- Create: `docker/start-local-dev.sh`
- Create: `docker/stop-local-dev.sh`
- Create: `docker/Dockerfile.migrations`
- Create: `Dockerfile`
- Create: `.env.example`

- [ ] **Step 1: Create `docker/compose.yml`**

```yaml
services:
  concilium-postgres:
    image: postgres:17
    container_name: concilium-postgres
    restart: unless-stopped
    environment:
      POSTGRES_USER: devuser
      POSTGRES_PASSWORD: devpass
      POSTGRES_MULTIPLE_DATABASES: concilium
    ports:
      - "5456:5432"
    volumes:
      - concilium_postgres_data:/var/lib/postgresql/data
      - ./init-multi-db.sh:/docker-entrypoint-initdb.d/init-multi-db.sh:ro
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U devuser -d concilium"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 20s

  concilium-minio:
    image: minio/minio:latest
    container_name: concilium-minio
    restart: unless-stopped
    command: server /data --console-address ":9001"
    environment:
      MINIO_ROOT_USER: minioadmin
      MINIO_ROOT_PASSWORD: minioadmin
    ports:
      - "9030:9000"
      - "9031:9001"
    volumes:
      - concilium_minio_data:/data
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:9000/minio/health/live"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s

volumes:
  concilium_postgres_data:
  concilium_minio_data:
```

- [ ] **Step 2: Create `docker/init-multi-db.sh`**

```bash
#!/usr/bin/env bash
set -euo pipefail

IFS=',' read -ra DBS <<< "$POSTGRES_MULTIPLE_DATABASES"
for db in "${DBS[@]}"; do
    db=$(echo "$db" | tr -d '[:space:]')
    echo "Creating database: $db"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        SELECT 'CREATE DATABASE "$db"' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$db')\gexec
        GRANT ALL PRIVILEGES ON DATABASE "$db" TO "$POSTGRES_USER";
EOSQL
done
```

- [ ] **Step 3: Create `docker/start-local-dev.sh`**

```bash
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
```

- [ ] **Step 4: Create `docker/stop-local-dev.sh`**

```bash
#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
cd "$PROJECT_ROOT"
docker compose -f docker/compose.yml down
echo "Concilium dev stack stopped."
```

- [ ] **Step 5: Make scripts executable**

```bash
chmod +x docker/start-local-dev.sh docker/stop-local-dev.sh docker/init-multi-db.sh
```

- [ ] **Step 6: Create `Dockerfile`**

```dockerfile
FROM gcr.io/distroless/java21
WORKDIR /app
COPY concilium-server/build/libs/concilium.jar ./concilium.jar
EXPOSE 8000
USER nonroot
ENTRYPOINT ["java", "-jar", \
  "-Dqqq.javalin.enableStaticFilesFromJar=true", \
  "-Dlog4j2.ignoreExceptions=true", \
  "-Djava.io.tmpdir=/tmp", \
  "/app/concilium.jar"]
```

- [ ] **Step 7: Create `docker/Dockerfile.migrations`**

```dockerfile
FROM liquibase/liquibase:4.30.0
USER root
RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/* \
    && rm -f /liquibase/bin/lpm \
    && chmod 755 /liquibase
USER liquibase
ADD --chmod=644 https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.2/postgresql-42.7.2.jar /liquibase/lib/postgresql.jar
COPY concilium-core/src/main/resources/db/ /liquibase/db/
ENTRYPOINT ["liquibase", \
  "--changelog-file=db/liquibase/changelog.yaml", \
  "--url=${LIQUIBASE_URL}", \
  "--username=${LIQUIBASE_USERNAME}", \
  "--password=${LIQUIBASE_PASSWORD}", \
  "update"]
```

- [ ] **Step 8: Create `.env.example`**

```bash
# Database
RDBMS_VENDOR=postgresql
RDBMS_HOSTNAME=localhost
RDBMS_PORT=5456
RDBMS_DATABASE_NAME=concilium
RDBMS_USERNAME=devuser
RDBMS_PASSWORD=devpass

# S3/MinIO
S3_ENDPOINT=http://localhost:9030
S3_BUCKET_NAME=concilium-artifacts
S3_ACCESS_KEY=minioadmin
S3_SECRET_KEY=minioadmin
S3_REGION=us-east-1

# Auth
AUTH_MODE=dev_local

# Server
SERVER_PORT=8000

# Logging
LOG_LEVEL=INFO
QQQ_LOG_LEVEL=INFO
```

- [ ] **Step 9: Verify Docker stack starts**

```bash
docker compose -f docker/compose.yml up -d
```

Wait for healthy, then:

```bash
docker exec concilium-postgres pg_isready -U devuser -d concilium
curl -sf http://localhost:9030/minio/health/live
```

Expected: Both succeed. Then:

```bash
docker compose -f docker/compose.yml down
```

- [ ] **Step 10: Commit**

```bash
git add docker/ Dockerfile .env.example
git commit -m "feat: add Docker dev stack with Postgres and MinIO"
```

---

## Task 7: Liquibase Placeholder

**Files:**
- Create: `concilium-core/src/main/resources/db/liquibase/changelog.yaml`
- Create: `concilium-core/src/main/resources/db/liquibase/liquibase.properties`

- [ ] **Step 1: Create changelog.yaml**

```yaml
databaseChangeLog: []
```

- [ ] **Step 2: Create liquibase.properties**

```properties
changeLogFile=db/liquibase/changelog.yaml
url=jdbc:postgresql://${RDBMS_HOSTNAME}:${RDBMS_PORT}/${RDBMS_DATABASE_NAME}
username=${RDBMS_USERNAME}
password=${RDBMS_PASSWORD}
```

- [ ] **Step 3: Commit**

```bash
git add concilium-core/src/main/resources/db/
git commit -m "feat: add Liquibase changelog placeholder"
```

---

## Task 8: CI/CD Configuration

**Files:**
- Create: `.circleci/config.yml`
- Create: `.munitor.yml`

- [ ] **Step 1: Create `.circleci/config.yml`**

```yaml
version: 2.1
setup: true
orbs:
  munitor: kof22/munitor@dev:snapshot
workflows:
  setup:
    jobs:
      - munitor/generate_pipeline
```

- [ ] **Step 2: Create `.munitor.yml`**

Note: Munitor's `java-webapp` pipeline assumes Maven. This config is the starting point; adjustments may be needed for Gradle builds. The build command override tells Munitor to use Gradle instead of Maven.

```yaml
pipeline: java-webapp
orb_version: "0.1"

image_name: concilium
java_version: "21"

docker:
  registry: ghcr.io/koftwentytwo

cd:
  repo: KofTwentyTwo/Concilium-CD
  format: kustomize
  env:
    develop: dev

coverage:
  min_instruction: "50"

sast:
  fail_on_findings: true

sbom: true
owasp: false

health:
  path: /api/health
  port: "8000"

contexts:
  registry: ghcr
  github: github
```

- [ ] **Step 3: Commit**

```bash
mkdir -p .circleci
git add .circleci/config.yml .munitor.yml
git commit -m "feat: add CircleCI and Munitor CI/CD configuration"
```

---

## Task 9: .gitignore and .editorconfig

**Files:**
- Modify: `.gitignore`
- Create: `.editorconfig`

- [ ] **Step 1: Update `.gitignore`**

```gitignore
# Gradle
.gradle/
build/
!gradle/wrapper/gradle-wrapper.jar

# Maven (in case QQQ tools generate any)
target/

# IntelliJ IDEA
.idea/
*.iml
*.iws
*.ipr
out/

# Java
*.class
*.jar
*.war
*.ear
hs_err_pid*

# Node / Frontend
frontend/node_modules/
frontend/.next/
frontend/out/

# Environment
.env
.env.local
.env.*.local

# OS
.DS_Store
Thumbs.db

# Logs
*.log
log/

# Docker
docker/.cache/
```

- [ ] **Step 2: Create `.editorconfig`**

```editorconfig
root = true

[*]
end_of_line = lf
insert_final_newline = true
trim_trailing_whitespace = true
charset = utf-8

[*.java]
indent_style = space
indent_size = 3

[*.{kt,kts}]
indent_style = space
indent_size = 3

[*.{yml,yaml}]
indent_style = space
indent_size = 2

[*.{json,toml}]
indent_style = space
indent_size = 2

[*.{ts,tsx,js,jsx}]
indent_style = space
indent_size = 2

[*.md]
trim_trailing_whitespace = false

[Makefile]
indent_style = tab
```

- [ ] **Step 3: Remove .idea/ from git tracking**

```bash
git rm -r --cached .idea/ 2>/dev/null || true
```

- [ ] **Step 4: Commit**

```bash
git add .gitignore .editorconfig
git commit -m "chore: update gitignore for Gradle/Node/IntelliJ and add editorconfig"
```

---

## Task 10: Frontend Placeholder

**Files:**
- Create: `frontend/package.json`
- Create: `frontend/next.config.mjs`
- Create: `frontend/tsconfig.json`
- Create: `frontend/tailwind.config.ts`
- Create: `frontend/postcss.config.cjs`
- Create: `frontend/src/app/layout.tsx`
- Create: `frontend/src/app/page.tsx`
- Create: `frontend/src/styles/globals.css`

- [ ] **Step 1: Create `frontend/package.json`**

```json
{
  "name": "concilium-frontend",
  "version": "0.1.0",
  "private": true,
  "type": "module",
  "engines": {
    "node": ">=22"
  },
  "scripts": {
    "dev": "next dev --port 3000",
    "build": "next build",
    "start": "next start",
    "lint": "eslint src"
  },
  "dependencies": {
    "next": "^15.3.0",
    "react": "^19.1.0",
    "react-dom": "^19.1.0",
    "tailwindcss": "^4.1.0",
    "@tailwindcss/postcss": "^4.1.0"
  },
  "devDependencies": {
    "@types/node": "^22.0.0",
    "@types/react": "^19.1.0",
    "@types/react-dom": "^19.1.0",
    "typescript": "^5.8.0"
  }
}
```

- [ ] **Step 2: Create `frontend/next.config.mjs`**

```javascript
/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8000/api/:path*',
      },
      {
        source: '/ws/:path*',
        destination: 'http://localhost:8000/ws/:path*',
      },
    ];
  },
};

export default nextConfig;
```

- [ ] **Step 3: Create `frontend/tsconfig.json`**

```json
{
  "compilerOptions": {
    "target": "ES2017",
    "lib": ["dom", "dom.iterable", "esnext"],
    "allowJs": true,
    "skipLibCheck": true,
    "strict": true,
    "noEmit": true,
    "esModuleInterop": true,
    "module": "esnext",
    "moduleResolution": "bundler",
    "resolveJsonModule": true,
    "isolatedModules": true,
    "jsx": "preserve",
    "incremental": true,
    "plugins": [{ "name": "next" }],
    "paths": { "@/*": ["./src/*"] }
  },
  "include": ["next-env.d.ts", "**/*.ts", "**/*.tsx"],
  "exclude": ["node_modules"]
}
```

- [ ] **Step 4: Create `frontend/tailwind.config.ts`**

```typescript
import type { Config } from "tailwindcss";

const config: Config = {
  content: ["./src/**/*.{ts,tsx}"],
};

export default config;
```

- [ ] **Step 5: Create `frontend/postcss.config.cjs`**

```javascript
module.exports = {
  plugins: {
    "@tailwindcss/postcss": {},
  },
};
```

- [ ] **Step 6: Create `frontend/src/app/layout.tsx`**

```tsx
import type { Metadata } from "next";
import "@/styles/globals.css";

export const metadata: Metadata = {
  title: "Concilium",
  description: "Master-agent orchestration platform",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  );
}
```

- [ ] **Step 7: Create `frontend/src/app/page.tsx`**

```tsx
export default function Home() {
  return (
    <main className="flex min-h-screen items-center justify-center">
      <div className="text-center">
        <h1 className="text-4xl font-bold">Concilium</h1>
        <p className="mt-4 text-lg text-gray-600">
          Command Center coming soon
        </p>
      </div>
    </main>
  );
}
```

- [ ] **Step 8: Create `frontend/src/styles/globals.css`**

```css
@import "tailwindcss";
```

- [ ] **Step 9: Install dependencies and verify**

```bash
cd /Users/james.maes/Git.Local/Kof22/Concilium/frontend && npm install
```

Then:

```bash
npm run build
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 10: Commit**

```bash
cd /Users/james.maes/Git.Local/Kof22/Concilium
git add frontend/package.json frontend/package-lock.json frontend/next.config.mjs frontend/tsconfig.json frontend/tailwind.config.ts frontend/postcss.config.cjs frontend/src
git commit -m "feat: add Next.js frontend placeholder"
```

---

## Task 11: README

**Files:**
- Modify: `README.md`

- [ ] **Step 1: Update README.md**

```markdown
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
```

- [ ] **Step 2: Commit**

```bash
git add README.md
git commit -m "docs: update README with quick start and architecture reference"
```

---

## Verification

After all tasks are complete, run the full verification:

- [ ] **Full build passes:** `./gradlew build`
- [ ] **Shadow JAR builds:** `./gradlew :concilium-server:shadowJar`
- [ ] **Docker stack starts:** `docker compose -f docker/compose.yml up -d` (then `down`)
- [ ] **Frontend builds:** `cd frontend && npm run build`
- [ ] **All tests pass:** `./gradlew test`
