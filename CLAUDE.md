# CLAUDE.md

# Concilium Bootstrap Instructions for Claude Code

## Purpose

You are helping bootstrap a new software project named **Concilium**.

Your task is to take the existing project proposal and turn it into a real, durable, implementation-ready project foundation inside this GitHub repository.

This is not a toy prototype. Optimize for a strong long-term engineering foundation.

---

## Project Summary

Concilium is a **master-agent orchestration platform for multi-repository software delivery**.

It introduces the concept of a **Master Project**:

- a single logical software system
- composed of multiple repositories
- coordinated by a persistent **Master Agent**
- with one persistent **Repo Agent** per attached repository
- plus optional **Specialist Agents** for focused functions like QA, architecture, CI/CD, release, and documentation

Concilium is **planner/coordinator first**. It is not primarily a coding chatbot. Its role is to coordinate planning, issue-backed work, architecture awareness, progress tracking, and delivery convergence across multiple repos.

---

## Core Product Requirements

You must preserve and design around these requirements:

### 1. Master Project
The system must support a first-class Master Project object that groups multiple repositories into one logical software system.

### 2. Persistent Master and Repo Agents
The system must support:

- a persistent Master Agent per Master Project
- a persistent Repo Agent per repository
- optional Specialist Agents

These are durable identities even if runtime execution is on demand.

### 3. Memory Must Persist Across Machines and Sessions
The system must support long-term durable memory for:

- Master Projects
- Repo Agents
- Specialist Agents
- architecture
- plans
- execution history
- issue-linked history
- validation history

This memory must work across home/work machines and different runtime environments.

### 4. Human- and System-Editable Architecture Model
The architecture model is a first-class system artifact and must support:

- human editing
- system updates/inference
- versioning
- drift detection between declared and observed architecture

### 5. Issue Tracker Is Mandatory
**100% of task creation and coordination must use Jira or GitHub Issues.**

This requirement is strict.

Every actionable work item must exist in the tracker as:

- issue
- epic
- task
- story
- subtask
- linked issue
- or equivalent native construct

Concilium may maintain metadata, but it must not invent a hidden parallel backlog.

### 6. CI/CD Awareness
The system must understand CI/CD as part of delivery coordination.

Initial design must explicitly account for:

- Munitor
- CircleCI
- repo-specific validation pipelines

### 7. Hybrid Execution
The system must support execution in:

- local environments
- remote runners
- Kubernetes jobs
- hybrid combinations

### 8. Different Models Per Agent
The system must support different LLM providers/models per agent and/or task.

### 9. Initial User Is a Single Engineer
Optimize early UX, workflow, and implementation for a single power user.

---

## Product Philosophy

When in doubt, preserve these principles:

- planner/coordinator first
- durable system over flashy demo
- traceability over hidden magic
- architecture-aware orchestration
- human-governed automation
- project-level intelligence, not only repo-level intelligence
- multi-agent design, not giant single-agent loops

---

## Design Expectations

You are expected to produce an opinionated design. Do not stop at vague options unless tradeoffs truly matter.

When multiple reasonable implementations are possible:

1. briefly identify the main options
2. explain tradeoffs
3. recommend one
4. scaffold toward the recommendation

Favor pragmatic, durable choices.

---

## Preferred Architectural Direction

Use this as the default unless you identify a clearly superior alternative.

### Control Plane
A central Concilium control plane should own:

- Master Projects
- repos
- agent registrations
- architecture records
- plans
- work mappings
- issue mappings
- approvals
- audit history
- configuration

### Persistence
Target a layered persistence design using:

- QQQ-based backend for canonical business/control records
- Postgres for structured durable state
- S3-compatible object storage for larger artifacts
- semantic/vector retrieval for knowledge recall
- durable orchestration persistence for long-running workflows

Do not treat any one of these as the entire memory system.

### Memory Model
Use a layered memory model separating:

- operational state
- episodic/event history
- knowledge documents and summaries
- large artifacts
- semantic recall

### Runtime Model
Prefer:

- durable agent identities
- on-demand runtime execution
- resumable workflows
- explicit orchestration state

### Work Tracking
All work must map to Jira or GitHub Issues.

### Source Control
Assume GitHub as the initial primary source-control platform.

---

## What You Should Produce

You should expand this repository into a practical starting point for active development.

### 1. Documentation
Create or refine:

- `README.md`
- product vision doc
- PRD
- architecture overview
- domain model
- workflow definitions
- memory strategy
- orchestration strategy
- integration strategy
- ADRs
- roadmap
- setup docs

### 2. Repo Structure
Create a clean initial repo structure for implementation.

Include things like:

- source directories
- docs directories
- ADR directories
- config templates
- test directories
- examples
- issue templates
- development bootstrap files

### 3. Initial Technical Foundation
Create starter implementation scaffolding for:

- core domain entities
- service boundaries
- module layout
- config strategy
- initial APIs or service skeletons
- test scaffolding

Do not overbuild, but do leave a coherent foundation.

### 4. Project Management Assets
Create:

- initial milestones
- implementation phases
- backlog recommendations
- issue templates
- bootstrap tasks
- initial epics / workstreams

### 5. Examples
Provide at least one example of:

- a Master Project definition
- a repo registration
- an architecture model
- an issue decomposition flow
- a message-passing example

---

## Files You Should Likely Read and then Create if missing or needing updating

This is guidance, not an absolute list.

### Root
- `README.md`
- `PROJECT_PROPOSAL.md`
- `CLAUDE.md`
- `.gitignore`
- `.editorconfig`
- `.env.example`

### Docs
- `docs/prd.md`
- `docs/architecture/overview.md`
- `docs/architecture/domain-model.md`
- `docs/architecture/memory-model.md`
- `docs/architecture/orchestration.md`
- `docs/architecture/message-protocol.md`
- `docs/architecture/integrations.md`
- `docs/roadmap.md`
- `docs/workflows.md`

### ADRs
- `docs/adr/0001-*.md`
- `docs/adr/0002-*.md`
- etc.

### Examples
- `examples/master-project-example.*`
- `examples/architecture-model-example.*`
- `examples/message-example.*`

### GitHub Meta
- `.github/ISSUE_TEMPLATE/...`
- `.github/PULL_REQUEST_TEMPLATE.md`
- `.github/workflows/...` only if useful for bootstrapping

### Source
Create an initial source structure consistent with your recommended stack.

---

## Important Constraints

### 1. Keep It Cohesive
Do not create a random collection of disconnected docs. Everything should align around one coherent architecture and roadmap.

### 2. Keep It Production-Minded
Scaffold for real development, not only ideation.

### 3. Respect the Product Direction
Do not simplify away the Master Project concept, persistent agents, tracker-native work, or architecture model.

### 4. Avoid Hidden Task Graph Assumptions
Internal orchestration metadata is acceptable, but official work must remain tracker-backed.

### 5. Design for Growth
Even though the first user is a single engineer, do not corner the design into a dead-end that cannot later support teams.

---

## Decisions You Should Explicitly Make

Please make and document recommendations for:

- language/runtime stack
- service architecture shape
- orchestrator approach
- persistence approach
- message schema direction
- architecture graph representation
- issue tracker abstraction model
- repo execution model
- local vs remote runtime handling
- MVP boundaries

When possible, choose one.

---

## Quality Bar

Your output should be:

- opinionated
- structured
- implementation-oriented
- internally consistent
- easy for an engineer to start building from

The result should feel like the first strong foundation of a serious open-source or commercial software product.

---

## Immediate Task

Using the project proposal as the main source of truth, fully flesh out the project and scaffold everything needed in this repository so active development can begin.

Start by:

1. refining the repository structure
2. generating the core docs
3. defining the architecture and domain model
4. proposing the implementation stack
5. creating initial scaffolding and project-management artifacts

Then continue until the repo has a clean, coherent starting point.
