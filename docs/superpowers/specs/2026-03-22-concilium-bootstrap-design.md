# Concilium Bootstrap Design Spec

## 1. System Overview

Concilium is a master-agent orchestration platform for multi-repository software delivery. It coordinates planning, issue-backed work, architecture awareness, progress tracking, and delivery convergence across multiple repos using persistent AI agents.

**Architecture shape:** Monolith-first QQQ application with a hybrid agent execution model.

**Three layers:**

- **Control Plane (QQQ Application)** -- Owns all state: Master Projects, agent registrations, architecture models, plans, work mappings, messages, memory, execution history, audit trail. Exposes REST API via QQQ/Javalin and admin UI via qqq-frontend-material-dashboard. Backed by Postgres.
- **Orchestration Engine (Internal Module)** -- Manages agent lifecycle and workflow execution. Determines when agents run, assembles their context, enforces governance policies, and processes results. Durable workflow state for long-running initiatives.
- **Agent Runtime (Claude Code CLI + Claude API)** -- Executes agent work. Heavy reasoning via Claude Code CLI processes. Lightweight tasks via Claude API with Haiku.

**Primary UX:** Custom Next.js Command Center frontend with a live agent graph showing real-time execution status. Runs as a separate Node process in the same repo.

---

## 2. Technology Stack

| Layer | Choice | Version |
|---|---|---|
| Language | Java | 21 |
| Build | Gradle (Kotlin DSL) | Latest |
| Backend Framework | QQQ | 0.40.0-SNAPSHOT (mavenLocal) |
| Web Server | Javalin (via QQQ) | Bundled with QQQ |
| Admin UI | qqq-frontend-material-dashboard | Bundled with QQQ |
| Database | PostgreSQL | 17 |
| Migrations | Liquibase | 4.x (aligned with QQQ ecosystem) |
| Object Storage | S3-compatible (MinIO for dev) | Latest |
| Frontend | Next.js + React + Tailwind CSS | Latest stable / 19 / 4 |
| Graph Visualization | React Flow | Latest |
| Real-time | WebSocket (Javalin native) | -- |
| CI/CD | CircleCI + Munitor orb | Latest |
| Container | distroless/java21 (backend), Node (frontend) | -- |
| Deployment | Kustomize | -- |
| IDE | IntelliJ IDEA | -- |
| Issue Tracker | GitHub Issues | -- |
| Source Control | GitHub | KofTwentyTwo/Concilium |

**Build system note:** QQQ itself is Maven-based, but its artifacts are published to Maven Central and available via `mavenLocal()`. Gradle can consume Maven dependencies without issue. A Phase 1 spike task will validate Gradle + QQQ SNAPSHOT compatibility before full implementation begins. The Claritas project (same org) is a working Gradle Kotlin DSL multi-module project, confirming this is a supported pattern in the KofTwentyTwo ecosystem.

---

## 3. Gradle Project Structure

```
concilium/
  settings.gradle.kts
  build.gradle.kts
  gradle.properties
  gradlew / gradlew.bat
  gradle/
    wrapper/
    libs.versions.toml

  concilium-core/
    build.gradle.kts
    src/main/java/com/kof22/concilium/
      model/
        project/
          MasterProject.java
          Repository.java
        agent/
          Agent.java
          AgentMemory.java
          AgentExecution.java
        architecture/
          ArchitectureModel.java
          ArchitectureComponent.java
          ArchitectureDependency.java
        work/
          Plan.java
          WorkItem.java
          WorkItemLink.java
        communication/
          AgentMessage.java
        orchestration/
          Workflow.java
          WorkflowStep.java
        audit/
          AuditEntry.java
      metadata/
        apps/
        auth/
    src/main/resources/
      db/liquibase/
        changelog.yaml
        changesets/
        datasets/
    src/test/java/com/kof22/concilium/

  concilium-orchestration/
    build.gradle.kts
    src/main/java/com/kof22/concilium/orchestration/
      engine/
        WorkflowEngine.java
        WorkflowStepExecutor.java
      workflows/
        InitiativeDecompositionWorkflow.java
        RepoTaskExecutionWorkflow.java
        ValidationCycleWorkflow.java
        ProgressReconciliationWorkflow.java
      governance/
        GovernanceGate.java
        ApprovalPolicy.java
      context/
        AgentContextAssembler.java
        MemorySelector.java
      result/
        ResultCaptureProcessor.java
        MemoryUpdateProcessor.java

  concilium-integrations/
    build.gradle.kts
    src/main/java/com/kof22/concilium/integrations/
      github/
        GitHubClient.java
        GitHubIssueSync.java
        GitHubWebhookHandler.java
      circleci/
        CircleCIClient.java
        CircleCIPipelineMonitor.java
      claude/
        ClaudeCodeExecutor.java
        ClaudeApiExecutor.java
        ClaudeContextFormatter.java

  concilium-memory/
    build.gradle.kts
    src/main/java/com/kof22/concilium/memory/
      MemoryService.java
      MemoryConsolidator.java
      ArtifactStore.java

  concilium-server/
    build.gradle.kts
    src/main/java/com/kof22/concilium/
      ConciliumServer.java
      ConciliumCli.java
      metadata/
        ConciliumMetaDataProvider.java
    src/main/resources/
      log4j2.xml

  frontend/
    package.json
    next.config.mjs
    tsconfig.json
    tailwind.config.ts
    src/
      app/
      api/
      ws/
      components/
      views/
        AgentGraph/
        Dashboard/
        ExecutionFeed/
        WorkBoard/
      stores/
      styles/

  docker/
    compose.yml
    start-local-dev.sh
    stop-local-dev.sh
    Dockerfile.migrations
    init-multi-db.sh

  Dockerfile
  .munitor.yml
  .circleci/config.yml
  .editorconfig
  .env.example
  .gitignore
  CLAUDE.md
  README.md
  LICENSE

  docs/
    prd.md
    roadmap.md
    workflows.md
    architecture/
      overview.md
      domain-model.md
      memory-model.md
      orchestration.md
      agent-execution.md
      message-protocol.md
      integrations.md
    adr/
      0001-java-gradle-qqq-stack.md
      0002-hybrid-agent-execution.md
      0003-configurable-governance.md
      0004-layered-memory-model.md

  examples/
    master-project.json
    repository-registration.json
    architecture-model.json
    agent-message.json

  .github/
    ISSUE_TEMPLATE/
      epic.md
      story.md
      task.md
      bug.md
    PULL_REQUEST_TEMPLATE.md
```

**Module dependency graph:**
```
concilium-server  -->  concilium-orchestration  -->  concilium-core
                  -->  concilium-integrations    -->  concilium-core
                  -->  concilium-memory          -->  concilium-core
                  -->  concilium-core
```

No circular dependencies. Core knows nothing about the other modules. Server wires everything together.

**Multi-module note:** Both QQQ reference apps (sample project, Website-Backend) are single-module. This multi-module structure is a deliberate design choice for separation of concerns. A Phase 1 spike task will validate that `MetaDataProducerHelper.processAllMetaDataProducersInPackage()` correctly discovers `@QMetaDataProducingEntity` classes across module boundaries when all modules are on the classpath. If discovery fails across JARs, entities will be consolidated into `concilium-core` with package-level separation instead.

---

## 4. Domain Model

All entities use QQQ's `@QMetaDataProducingEntity` annotation with JPA annotations, following the Website-Backend pattern. Entities auto-produce their own table metadata. All ID and FK fields use `Long` (not `Integer`), consistent with QQQ conventions.

### 4.1 Project Domain

| Entity | Table | Purpose |
|---|---|---|
| `MasterProject` | `master_project` | Top-level container. Name, description, goals, status, milestones. |
| `Repository` | `repository` | Registered repo. Git URL, role, branch strategy, clone path. FK to MasterProject. |

### 4.2 Architecture Domain

| Entity | Table | Purpose |
|---|---|---|
| `ArchitectureModel` | `architecture_model` | Versioned architecture snapshot. JSON graph. FK to MasterProject. `sourceType` field distinguishes DECLARED (human-defined) vs OBSERVED (system-inferred) models to support drift detection. |
| `ArchitectureComponent` | `architecture_component` | Node in arch graph (service, lib, API, infra). FK to ArchitectureModel + Repository. |
| `ArchitectureDependency` | `architecture_dependency` | Edge between components. Type, direction. |

### 4.3 Agent Domain

| Entity | Table | Purpose |
|---|---|---|
| `Agent` | `agent` | Durable identity. Type (master/repo/specialist), model config, system prompt, tool permissions, approval policy. FK to MasterProject and optionally Repository. |
| `AgentMemory` | `agent_memory` | Per-agent memory entries. Type (operational/episodic/knowledge/summary), content, relevance score. FK to Agent. |
| `AgentExecution` | `agent_execution` | Every invocation logged. Prompt hash, structured output, tool calls, token usage, duration, cost estimate. FK to Agent. |

**Agent types:** master, repo, specialist. Specialist subtypes: qa, architecture, cicd, docs, release, issueManagement. All subtypes are defined in the type enum from day one, even if specialist agents are not implemented until Phase 2.

### 4.4 Work Domain

| Entity | Table | Purpose |
|---|---|---|
| `Plan` | `plan` | Project or repo-level plan. Goal, approach, steps as JSON, rollout/rollback notes. FK to MasterProject. |
| `WorkItem` | `work_item` | Maps to a GitHub Issue. Issue number, repo, tracker type, external URL, item type (epic/story/task/subtask), status, assigned agent. |
| `WorkItemLink` | `work_item_link` | Relationships: blocks, depends-on, parent-child, related-to. |

### 4.5 Communication Domain

| Entity | Table | Purpose |
|---|---|---|
| `AgentMessage` | `agent_message` | Inter-agent message. Sender, recipient, type, payload, status (pending/delivered/acknowledged), linked work item, referenced artifacts. |

**Message types:** assignment, planRequest, contextPackage, clarificationRequest, blocker, dependencyUpdate, executionUpdate, validationResult, escalation, completionSummary.

### 4.6 Orchestration Domain

| Entity | Table | Purpose |
|---|---|---|
| `Workflow` | `workflow` | Durable workflow instance. Type, current state, waiting-on condition. FK to MasterProject. |
| `WorkflowStep` | `workflow_step` | Step within a workflow. Status, I/O refs, approval requirement. FK to Workflow. |

### 4.7 Audit Domain

| Entity | Table | Purpose |
|---|---|---|
| `AuditEntry` | `audit_entry` | Immutable log. Who/what did what, when, to which entity. Links to agent executions, work items, plan changes, architecture updates. |

### 4.8 Backends

| Backend | Name | MetaData Class | Module Dependency | Purpose |
|---|---|---|---|---|
| PostgreSQL | `"postgres"` | `PostgreSQLBackendMetaData` | `qqq-backend-module-postgres` | Primary storage for all entities |
| Memory | `"memory"` | `QBackendMetaData` with `backendType=MemoryBackendModule.class` | `qqq-backend-core` | Transient operational state during agent execution |
| S3 | `"s3"` | `S3BackendMetaData` | `qqq-backend-module-filesystem` | Large artifacts (conversation logs, architecture snapshots, documents) |

---

## 5. Agent Execution Model

### 5.1 Agent Identity

Each agent is a record in the `Agent` table with durable identity:
- Type: master, repo, specialist (qa, architecture, cicd, docs, release, issueManagement)
- Assigned Master Project and optionally a specific Repository
- Model config: modelId, maxTurns, temperature
- System prompt template: base instructions for the agent's role
- Tool permissions: comma-separated list of allowed Claude Code tools
- Approval policy: NONE, RISK_BASED, or ALWAYS

### 5.2 Hybrid Execution

| Mode | Runtime | When Used |
|---|---|---|
| Claude Code CLI | `claude -p --output-format json --permission-mode accept` | Heavy reasoning: planning, code analysis, decomposition, implementation |
| Claude API (Haiku) | Anthropic SDK via HTTP | Lightweight: classification, routing, summarization |

### 5.3 CLI Invocation

```
claude -p \
  --output-format json \
  --system-prompt "$(cat /tmp/concilium/agent-{id}-context.md)" \
  --permission-mode accept \
  --allowedTools "{agent.toolPermissions}" \
  --max-turns {agent.maxTurns} \
  "{task.prompt}"
```

Working directory set to target repo for Repo Agents. Concilium workspace for Master/Specialist Agents.

### 5.4 Tool Permissions Per Agent Type

| Agent Type | Allowed Tools | Working Dir |
|---|---|---|
| Master Agent | Read, Grep, Glob, Bash (read-only), GitHub MCP | Concilium workspace |
| Repo Agent | Read, Write, Edit, Bash, Grep, Glob, Git operations | Target repo directory |
| Specialist (QA) | Read, Bash (test runners), Grep, Glob | Target repo directory |
| Specialist (Architecture) | Read, Grep, Glob | Cross-repo (read-only) |
| Specialist (CI/CD) | Read, CircleCI MCP, GitHub MCP | Concilium workspace |

Jira MCP tools will be added to relevant agent types when Jira integration is implemented (Phase 2+).

### 5.5 Execution Lifecycle (QQQ Process)

1. **ContextAssemblyStep** -- Gather agent identity, relevant memories, architecture context, linked work items, task details. Generate temporary system prompt.
2. **GovernanceGateStep** -- Check approval policy. If NONE, pass through. If required, pause workflow.
3. **ExecutionStep** -- Invoke Claude Code CLI or Claude API. Capture structured JSON output.
4. **ResultCaptureStep** -- Parse output. Extract tool calls, files changed, issues created, decisions, messages to other agents.
5. **MemoryUpdateStep** -- Store execution record. Update agent memory with new learnings. Update work item status.
6. **DispatchStep** -- If agent produced messages to other agents, enqueue next invocation. Initially inline; RabbitMQ later.

### 5.6 Inter-Agent Communication

Agents never communicate directly. All messages flow through Concilium:

```
Master Agent  -->  [AgentMessage in DB]  -->  Orchestrator  -->  [Invoke]  -->  Repo Agent
                                                                                     |
Orchestrator  <--  [AgentMessage in DB]  <--  Repo Agent response  <-----------------+
```

---

## 6. Orchestration and Workflow Engine

### 6.1 Core Workflow Types

**Initiative Decomposition:**
1. Master Agent analyzes initiative against architecture model
2. Decomposes into repo-scoped work items
3. [Governance Gate if configured] User reviews decomposition
4. Work items created as GitHub Issues
5. Dependencies linked
6. Repo Agents assigned

**Repo Task Execution:**
1. Repo Agent receives context (work item, plan, architecture constraints)
2. Creates feature branch
3. Implements (may run multiple execution cycles)
4. [Governance Gate if configured] User reviews
5. PR created, linked to GitHub Issue
6. CI/CD signal awaited
7. Completion message to Master Agent

**Validation Cycle:**
1. QA Specialist reviews changes across affected repos
2. CI/CD Specialist checks pipeline status
3. Architecture Specialist checks for drift
4. Results aggregated, blockers surfaced
5. [Governance Gate if configured] User reviews summary

**Progress Reconciliation:**
1. Master Agent queries all active work items
2. Gathers status from GitHub Issues, CI/CD, Repo Agent reports
3. Updates Master Project status and risk assessment
4. Surfaces blockers, stale items, dependency conflicts

### 6.2 Waiting Conditions

Workflows can pause on:
- Human approval (when governance policy requires it)
- GitHub Issue status change
- CI/CD pipeline completion
- Another workflow's completion
- Scheduled time

MVP uses polling for external signals. RabbitMQ adds event-driven triggers later.

---

## 7. Governance Model

### 7.1 Agent-Level Configuration

| Field | Type | Purpose |
|---|---|---|
| `approvalPolicy` | Enum | `NONE` / `RISK_BASED` / `ALWAYS` |
| `autoApproveScope` | JSON | Actions this agent can take without approval |
| `escalationRules` | JSON | Conditions that force escalation regardless |

### 7.2 Policies

- **NONE (MVP default):** Fully autonomous. Logged and auditable, no gates.
- **RISK_BASED:** Pauses only for configurable thresholds (issue count, protected paths, multi-repo changes, cost exceeds budget, agent signals low confidence).
- **ALWAYS:** Every execution requires approval.

### 7.3 Escalation Overrides (Always Apply)

- Destructive git operations (force push, branch delete)
- External mutations that can't be undone
- Agent explicitly flags uncertainty

### 7.4 Single-User Philosophy

PRs are the natural governance gate. Agent runs autonomously, you review code in PRs. Concilium logs everything for async review via audit trail and execution history.

---

## 8. Memory Model

### 8.1 Five Layers

| Layer | Storage | Purpose | Query Pattern |
|---|---|---|---|
| Operational State | Postgres (domain entities) | Live state of everything | Exact key, filter |
| Episodic Memory | Postgres (AgentExecution, AuditEntry) | What happened, when, by whom | Time range, entity link |
| Knowledge Documents | S3/Filesystem | Architecture summaries, repo analyses, decision records | Agent type + project + tags |
| Agent Working Memory | Postgres (AgentMemory) | Per-agent accumulated context | Agent + category + relevance |
| Semantic Recall | Future (pgvector) | Similarity search over knowledge | Embedding similarity |

### 8.2 AgentMemory Entity

```java
private Long    id;
private Long    agentId;
private String  memoryType;       // operational, episodic, knowledge, summary
private String  category;         // repo-conventions, architecture, user-preferences
private String  subject;          // short description for relevance matching
private String  content;          // the actual memory content
private Integer relevanceScore;   // agent can update
private Instant createDate;
private Instant modifyDate;
private Instant lastAccessedDate;
```

### 8.3 Context Assembly

When invoking an agent:
1. Load system prompt template
2. Query AgentMemory ordered by relevance + recency
3. Include top N memories within context budget
4. For Repo Agents: include repo-specific + Master Project memories
5. Include task, linked work items, architecture context
6. Assemble into system prompt passed to CLI

### 8.4 Memory Maintenance

After each execution:
- Lightweight Haiku call to summarize what the agent learned
- Store new memories, update relevance scores
- Consolidate old episodic entries into summaries
- Prune memories below relevance threshold after configured age

---

## 9. Frontend: Command Center

### 9.1 Stack

| Layer | Choice |
|---|---|
| Framework | Next.js (latest stable) |
| UI | React 19 |
| Styling | Tailwind CSS 4 + shadcn/ui |
| Graph | React Flow |
| State | Zustand |
| Charts | Recharts |
| Real-time | Native WebSocket |

### 9.2 Location

`frontend/` directory in the Concilium repo. Runs as its own Node process (port 3000). Proxies API calls to the backend (port 8000) via Next.js rewrites. Same pattern as Website-Frontend running alongside Website-Backend.

### 9.3 Core Views

**Live Agent Graph (main view):**
- Real-time node graph. Each agent is a node. Master at center, Repo Agents around it, Specialists branching off.
- Nodes show status (idle, executing, waiting, error) with visual indicators.
- Messages animate along edges as they flow between agents.
- Click node: current execution, recent memory, assigned work.
- Click edge: message history between agents.

**Project Dashboard:**
- Master Project status overview, milestone progress, active work items by status.
- Recent agent activity feed. Architecture model visualization.
- Blockers and risks surfaced.

**Execution Feed:**
- Real-time scrolling feed of agent actions.
- Filterable by agent, repo, work item.
- Expandable entries showing full tool calls and results.
- Cost/token tracking per execution.

**Work Board:**
- GitHub Issues as kanban board, grouped by repo or workflow stage.
- Agent assignment indicators. Links to GitHub.

### 9.4 Real-Time Events

WebSocket endpoint at `/ws/events` on the backend. Events pushed:
- `agent.execution.started` / `agent.execution.toolCall` / `agent.execution.completed`
- `agent.message.sent`
- `workItem.statusChanged`
- `workflow.stepCompleted`

---

## 10. Local Dev and Deployment

### 10.1 Local Dev (Docker Compose)

```
docker/
  compose.yml             -- Postgres 17 + MinIO
  start-local-dev.sh      -- One command: containers + migrations + seed + backend + frontend
  stop-local-dev.sh       -- Teardown
  Dockerfile.migrations   -- Liquibase migrations container
  init-multi-db.sh        -- Multi-database init
```

`start-local-dev.sh` boots: Docker services, waits for readiness, runs Liquibase migrations, starts backend JAR, starts frontend dev server. Redis will be added to the compose stack when RabbitMQ/caching needs arise (Phase 3).

### 10.2 Production (K8s)

- Backend container: `distroless/java21` (lean, secure)
- Frontend container: Node-based (Next.js standalone output)
- Migrations init container: `liquibase/liquibase`
- Agent execution: Spawned as local processes on the host or K8s Jobs (not inside the main container)
- CD repo: Kustomize overlays for dev/staging/prod

### 10.3 CI/CD

`.munitor.yml` with `java-webapp` pipeline type. Builds both backend JAR/container and frontend container. Quality gates: coverage, SAST, SBOM.

### 10.4 Future: RabbitMQ

QQQ supports RabbitMQ. Natural scaling path for async agent dispatch, event-driven orchestration (GitHub webhooks, CircleCI callbacks), and reliable message queuing. MVP uses direct invocation.

---

## 11. Integrations

### 11.1 GitHub

- Repository metadata, issue management, PR lifecycle
- GitHub MCP tools available to agents + gh CLI + REST API
- Bidirectional work item sync (Concilium creates issues; polling for external changes in Phase 2)

### 11.2 CircleCI / Munitor

- Pipeline status queries via CircleCI API
- Pass/fail signals for work items
- Munitor defines pipeline shape; Concilium reads results

### 11.3 Issue Tracker Abstraction

WorkItem entity maps to tracker items with `trackerType` field (github/jira). Adding Jira means adding an adapter, not redesigning the domain.

### 11.4 LLM Providers

- Claude Code CLI for heavy execution (subscription-based)
- Anthropic API Haiku for lightweight tasks
- Agent entity supports modelId per agent for future provider flexibility

---

## 12. Test Strategy

### 12.1 Frameworks

- JUnit 5 for unit and integration tests
- AssertJ for fluent assertions
- QQQ test patterns: `defineTestInstance()` with mock auth (`QAuthenticationType.MOCK`)

### 12.2 Database Strategy

- Unit tests: H2 in-memory via `RDBMSBackendMetaData` (same pattern as Website-Backend's test mode where `USE_POSTGRES=false`)
- Integration tests: Testcontainers with Postgres 17 for schema/migration validation
- QQQ's `MemoryBackendModule` for tests that do not require SQL

### 12.3 Coverage

- JaCoCo enforced via Gradle plugin
- Minimum 50% instruction coverage (aligned with Website-Backend)
- Minimum 90% class coverage

### 12.4 Test Structure

Each module has its own `src/test/java/` mirroring the main source tree. Test naming convention: `test{MethodName}_{scenario}_{expectedOutcome}`.

---

## 13. SDLC and GitHub Project Management

### 13.1 GitHub Project Board

GitHub Project (v2) with custom fields: Phase, Epic, Priority, Estimate, Agent Assignee.

### 13.2 Issue Hierarchy

| Level | GitHub Construct | Label Prefix |
|---|---|---|
| Epic | Issue with `epic` label | `epic:` |
| Story | Issue with `story` label | `story:` |
| Task | Issue with `task` label | `task:` |
| Subtask | Task list checkboxes within Task | -- |
| Bug | Issue with `bug` label | -- |

### 13.3 Labels

- **Type:** epic, story, task, bug, spike, chore
- **Phase:** phase:1, phase:2, phase:3, phase:4
- **Module:** mod:core, mod:orchestration, mod:integrations, mod:memory, mod:server, mod:frontend
- **Domain:** domain:agents, domain:architecture, domain:work-tracking, domain:memory, domain:ci-cd
- **Priority:** P0-critical, P1-high, P2-medium, P3-low
- **Status:** blocked, needs-design, needs-review

### 13.4 Issue Templates

- `epic.md` -- Goal, success criteria, child stories checklist
- `story.md` -- Linked epic, acceptance criteria, child tasks checklist
- `task.md` -- Linked story, implementation notes, definition of done
- `bug.md` -- Reproduce steps, expected vs actual, severity

### 13.5 Milestones

One GitHub Milestone per phase.

### 13.6 GitHub Wiki

Living documentation: architecture decisions, runbook, agent configuration guide, troubleshooting.

---

## 14. MVP Scope (Phase 1)

### Included

- Gradle multi-module project with build system, CI/CD pipeline, Docker dev stack
- Spike: validate Gradle + QQQ SNAPSHOT compatibility and multi-module metadata discovery
- All 15 domain entities with Liquibase migrations and QQQ metadata
- Agent execution engine: Claude Code CLI executor, context assembly, result capture, memory update
- Master Agent workflows: initiative decomposition, work item creation via GitHub Issues
- Repo Agent workflows: receive work, execute in repo, report results
- WebSocket real-time event system
- Command Center frontend: live agent graph, execution feed, project dashboard
- GitHub Issues integration, CircleCI status polling
- Governance defaults to NONE (fully autonomous)
- QQQ Admin Dashboard for data management

### Not Included (Later Phases)

- Specialist Agents (Phase 2)
- Workflow waiting conditions beyond polling (Phase 2)
- Jira integration (Phase 2)
- Semantic/vector memory (Phase 4)
- RabbitMQ async dispatch (Phase 3)
- OAuth2 authentication (Phase 3)
- Architecture drift detection (Phase 3)
- Multi-user support (Phase 4)
- Rollout/rollback orchestration (Phase 3)

### Phase 1 Epics

1. Project Foundation
2. Domain Model and Persistence
3. Agent Execution Engine
4. Master Agent Workflows
5. Repo Agent Workflows
6. Real-Time Event System
7. Command Center Frontend
8. Integration Layer
