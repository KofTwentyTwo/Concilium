# PROJECT_PROPOSAL.md

# Concilium
## Project Proposal and High-Level Requirements

## 1. Overview

**Concilium** is a master-agent orchestration platform for multi-repository software delivery.

It is designed to help a single engineer, and later teams, coordinate complex software systems that span multiple repositories by introducing a persistent **master project** abstraction. A master project represents one logical software system made up of multiple codebases, infrastructure definitions, documentation, pipelines, and related delivery assets.

Examples of a master project might include:

- `website-frontend`
- `website-backend`
- `website-cd`
- `website-shared`
- `website-docs`
- `website-infra`

Concilium should create and manage:

- a **master agent** that understands the overall project goals, milestones, architecture, dependencies, and state of delivery
- a **repo agent** for each repository that deeply understands its assigned codebase
- optional **specialist agents** for architecture, QA, documentation, CI/CD, release readiness, and issue management

Concilium is **planner/coordinator first**. It is not primarily a coding assistant. Its main purpose is to help plan, coordinate, track, and drive multi-repo initiatives to successful completion using persistent project knowledge, issue-tracker-backed workflows, and durable orchestration.

---

## 2. Vision

Build a durable software delivery control plane that can reason across repositories, architecture, issues, CI/CD, and agent execution to coordinate real project work from concept through integration readiness.

Concilium should become the operating system for a software initiative that spans more than one repo and more than one active line of work.

---

## 3. Product Thesis

Concilium exists to solve a problem that current repo-local coding agents do not fully solve:

A software system is rarely just one repo. Real delivery requires coordination across:

- multiple codebases
- infrastructure
- issue trackers
- branching strategies
- validation pipelines
- architecture constraints
- rollout sequencing
- human approvals
- cross-repo dependencies

Concilium should act as the coordinating intelligence for this environment.

Its core value is:

- understanding the full project
- maintaining persistent context
- decomposing initiatives into properly tracked work
- delegating work to repo-local and specialist agents
- coordinating dependencies and feedback loops
- helping drive the system toward a complete and working result

---

## 4. Initial User Profile

Initial target user:

- a **single engineer / technical lead / founder / CTO-style operator**

This user likely:

- owns several repos
- works across architecture, implementation, and delivery
- wants more than a code copilot
- needs continuity across machines and sessions
- wants strong SDLC traceability
- wants a reusable control plane for ongoing work

The initial UX and implementation should optimize for a single powerful operator while keeping the design extensible for future multi-user support.

---

## 5. Core Concepts

## 5.1 Concilium
The overall platform, product, and orchestration system.

## 5.2 Master Project
A first-class object representing one logical software system made up of multiple repositories and related workstreams.

A Master Project should own:

- project identity
- goals and outcomes
- milestones
- architecture model
- repo relationships
- work decomposition
- status and readiness

## 5.3 Master Agent
The persistent coordinating LLM identity for a Master Project.

The Master Agent should understand:

- project goals
- desired outcomes
- architecture
- dependencies across repos
- current work status
- blockers and risks
- rollout/integration progress

## 5.4 Repo Agent
A persistent repo-specific LLM identity.

Each Repo Agent should deeply understand:

- its repository structure
- code conventions
- modules and components
- build/test/deploy behavior
- local architecture
- repo-local branching and workflow norms

## 5.5 Specialist Agents
Optional agent types used for focused functions, such as:

- architecture agent
- QA/test agent
- documentation agent
- CI/CD agent
- release agent
- issue/project management agent

---

## 6. Guiding Principles

## 6.1 Planner and Coordinator First
Concilium should primarily orchestrate, plan, route, track, and reconcile work. It is not a chat wrapper around coding models.

## 6.2 Master Project as the Coordination Center
The Master Project should be the main conceptual container for goals, architecture, issue relationships, and delivery progress.

## 6.3 Tracker-Native Work
All actionable work must be represented through **Jira or GitHub Issues**. Concilium may hold internal metadata, but not a hidden shadow backlog that replaces the official work tracker.

## 6.4 Persistent, Cross-Machine Memory
Knowledge and state must survive across sessions, machines, and environments.

## 6.5 Human Governance
Concilium should be highly editable and governable by a human operator. The human remains in charge.

## 6.6 Architecture as a First-Class Artifact
Architecture must be modeled, stored, versioned, editable, and used in reasoning.

## 6.7 Multi-Agent by Design
The system must assume many cooperating agents, not one massive prompt loop.

## 6.8 Production-Minded Foundation
The project should be designed as a durable product foundation, not a throwaway prototype.

---

## 7. Problem Statement

Existing AI coding tools are increasingly strong at local implementation within a repo, but software delivery often fails at the coordination layer:

- work is not broken down well
- cross-repo changes are poorly synchronized
- architecture intent drifts from implementation reality
- issue trackers are incomplete or stale
- CI/CD outcomes are disconnected from planning
- decisions are lost across sessions and machines
- humans become the fragile integration layer

Concilium should reduce this gap by acting as the durable orchestration layer for software delivery.

---

## 8. Goals

## 8.1 Primary Goals

Concilium v1 should allow a user to:

- create a Master Project
- attach multiple repos
- establish persistent knowledge for project and repos
- define/edit a project architecture model
- use Jira or GitHub Issues for all work creation and coordination
- decompose work across repos
- delegate planned work to repo agents
- coordinate validation, dependencies, and progress
- persist and resume orchestration across sessions and machines

## 8.2 Longer-Term Goals

Future versions should expand toward:

- stronger automation
- richer specialist agents
- deeper CI/CD orchestration
- release coordination
- multi-user and team support
- broader platform integrations
- more autonomous, policy-controlled execution

---

## 9. Non-Goals for Initial Phase

Initial development should not overreach into all possible future features.

Likely non-goals for the first phase:

- full autonomous deployment to production
- large-scale team collaboration features
- full enterprise RBAC model
- marketplace/plugin ecosystem
- broad provider support beyond practical first integrations
- complete replacement of existing PM tools
- exhaustive self-healing agent autonomy

These may come later, but should not distract from establishing a strong core.

---

## 10. High-Level Functional Requirements

## 10.1 Master Project Management

Concilium must support:

- creating a Master Project
- naming and describing it
- defining goals, outcomes, milestones, and status
- attaching multiple repositories
- storing shared context across repos
- maintaining master-level summaries and memory
- surfacing master-level status and risks

A Master Project should serve as the umbrella object for coordinated software delivery.

---

## 10.2 Repository Registration and Ownership

Concilium must support:

- registering repos to a Master Project
- associating metadata to repos
- defining repo role or purpose within the larger project
- cloning, syncing, and updating repo state
- linking repos to issue tracker and CI/CD metadata
- tracking repo branch and PR history

Each repo must be a durable, queryable object in the platform.

---

## 10.3 Repo Agent Capabilities

Each repo should have a persistent Repo Agent identity.

Repo Agents must be able to:

- deeply understand their codebase
- maintain long-term repo-specific memory
- generate local plans for assigned work
- reason about local architecture and conventions
- understand build/test/deploy patterns
- collaborate with specialist agents when needed
- report progress, blockers, and validation results back to the Master Agent
- align work to Jira or GitHub Issues

Repo Agents should be persistent identities even if runtime processes are launched on demand.

---

## 10.4 Master Agent Capabilities

The Master Agent must be able to:

- understand overall project goals and milestones
- understand the architecture model
- understand relationships between repos
- break initiatives into issue-backed work
- assign and route work to repo agents and specialists
- reason about dependencies, sequencing, and blockers
- gather and reconcile outputs from multiple agents
- track progress toward system-level success
- reason about integration readiness and remaining gaps

The Master Agent is the project-level coordinating intelligence.

---

## 10.5 Specialist Agents

Concilium should support specialist agent roles, including but not limited to:

- Architecture
- QA/Test
- Documentation
- CI/CD
- Release/Integration
- Issue/PM

These should be modeled as pluggable agent capabilities rather than hard-coded one-off workflows.

---

## 10.6 Issue-Tracker-Native Tasking

Concilium must use **Jira or GitHub Issues for 100% of task creation and coordination**.

This requirement is mandatory.

Implications:

- every initiative must map to issues
- every decomposed task must exist as an issue, subtask, linked issue, epic, or equivalent construct
- all meaningful work must be traceable through the tracker
- branches, PRs, validations, and deliverables must map back to issue-tracked work
- Concilium may enrich tracker records with metadata, but not replace them

Concilium must treat issue tracking as part of the delivery contract, not as an optional integration.

---

## 10.7 Planning and Decomposition

Concilium must support:

- intake of a new initiative
- project-level planning
- decomposition into repo-scoped work
- decomposition into specialist-agent work
- dependency mapping
- sequencing
- acceptance criteria definition
- validation planning
- rollout and rollback planning
- progress tracking against plan

The system must distinguish between:

- goal / intent
- project plan
- issue-backed work items
- execution state
- validation state
- completion state

---

## 10.8 Architecture Model

Each Master Project must maintain a canonical architecture model.

This model must be:

- editable by humans
- editable by the system
- versioned
- queryable
- usable during planning and coordination

The architecture model should capture:

- repositories
- services/applications
- dependencies
- interfaces/APIs
- integration points
- infrastructure relationships
- deployment boundaries
- CI/CD relationships
- shared components/libraries

The system should support two views:

- **Declared Architecture**: human-defined intended structure
- **Observed Architecture**: system-inferred structure from repos and pipelines

Concilium should surface drift between these where possible.

---

## 10.9 Memory and Knowledge

Concilium must maintain persistent memory at multiple levels:

- Master Project memory
- Repo Agent memory
- Specialist Agent memory
- Architecture memory
- Work history
- Validation history
- Issue-linked historical context
- Artifact references
- Event/audit trail

Memory must be durable across:

- sessions
- machines
- runtime restarts
- execution environments

The system should use a layered memory model that distinguishes among:

- operational state
- episodic/event memory
- knowledge documents and summaries
- large artifacts
- semantic retrieval indexes

Concilium must not rely on raw chat history as its only memory model.

---

## 10.10 Messaging and Coordination

Concilium must support structured inter-agent messaging.

Messages should support types such as:

- assignment
- plan request
- context package
- clarification request
- blocker
- dependency update
- execution update
- validation result
- escalation
- completion summary

Messages should be associated with:

- master project
- repo
- work item / issue
- sender
- recipient
- timestamps
- status
- referenced artifacts

Claude Code should later define the exact schemas, protocol, and lifecycle.

---

## 10.11 Source Control Coordination

Concilium must be source-control-aware.

It must support reasoning and workflows around:

- repo state
- branch strategy
- branch creation
- branch naming conventions
- work item to branch linkage
- PR generation or coordination
- cross-repo relationships
- merge readiness
- integration sequencing

GitHub should be treated as the first practical SCM target.

---

## 10.12 CI/CD Awareness

Concilium must incorporate CI/CD as a core delivery signal.

It must be designed to understand and reason about:

- pipeline status
- required gates
- test/build outcomes
- validation pass/fail signals
- release readiness
- failure escalation

The initial design should explicitly account for:

- **Munitor**
- **CircleCI**
- repo-specific pipeline patterns and conventions

CI/CD must feed back into planning and work status.

---

## 10.13 Runtime and Execution Environments

Concilium must support execution in:

- local workstation environments
- remote runners
- Kubernetes jobs
- hybrid combinations of the above

Best-practice design assumption:

- agent identities are durable
- runtime execution is generally on-demand
- state and memory persist independently of active runtime processes

Concilium should support policies for where work may run.

---

## 10.14 Human Governance

Concilium must support strong human control, including:

- approval checkpoints
- editable plans
- editable architecture
- manual overrides
- review of issue creation and decomposition
- review of branch/PR actions
- review of system conclusions and summaries

The system should optimize for explainability and operator confidence.

---

## 10.15 Auditability and Traceability

Concilium must preserve traceability among:

- project goals
- plans
- issue tracker items
- agent actions
- branches
- pull requests
- CI/CD outcomes
- validation results
- completion summaries
- architecture updates

This should support serious SDLC and compliance-minded workflows.

---

## 11. Non-Functional Requirements

Concilium should be:

- durable
- resumable
- auditable
- secure
- observable
- extensible
- machine-independent
- provider-agnostic where practical
- deterministic in workflow state transitions where possible
- robust across partial failures

The architecture should be designed to scale later to:

- more repos
- more agents
- more workflows
- more users
- more teams

without forcing a total redesign.

---

## 12. Proposed Conceptual Architecture

This section sets direction, not final implementation.

## 12.1 Control Plane
A central control application should manage:

- Master Projects
- repos
- agent identities
- plans
- work mappings
- issue mappings
- architecture state
- approvals
- audit history
- system configuration

This should be the canonical coordination layer.

## 12.2 Memory and Knowledge Layer
A layered persistence model should support:

- structured records
- event history
- knowledge docs
- summaries
- artifacts
- semantic retrieval

## 12.3 Orchestration Layer
A durable orchestration model should support:

- long-running workflows
- retries
- waiting states
- issue-driven progression
- approval waits
- CI/CD waits
- resumable execution

## 12.4 Agent Runtime Layer
A pluggable runtime layer should support:

- Master Agent
- Repo Agents
- Specialist Agents
- structured task routing
- on-demand execution
- durable identities and memory

## 12.5 Integration Layer
Integration adapters should be designed for:

- GitHub
- Jira
- GitHub Issues
- CircleCI
- Munitor

with future extensibility for additional systems.

## 12.6 Operator UX Layer
The user-facing layer should eventually support:

- dashboarding
- project status
- repo status
- architecture view/editing
- issue coordination
- approvals
- execution history
- agent messaging and state

---

## 13. Storage and Persistence Direction

The intended platform direction is:

- **QQQ-based backend** for canonical application/control data
- **Postgres** for structured persistent records
- **S3-compatible object storage** for larger artifacts and snapshots
- **semantic/vector retrieval** for knowledge recall
- durable persistence for orchestration state

Important design constraint:

- do not treat S3 alone as memory
- do not treat vector storage alone as memory
- do not treat conversation history alone as memory

Concilium should use layered memory and durable state separation.

---

## 14. MVP Direction

An initial MVP should likely include:

- create a Master Project
- attach 2 to 5 repos
- persist repo summaries
- persist a project summary
- define/edit a basic architecture model
- create or sync issue-backed work items
- produce a project-level plan
- decompose work into repo plans
- assign work to repo agents
- store messages and progress
- monitor CI/CD signals
- produce integrated project status

This is enough to prove the core coordination value.

---

## 15. Deferred / Later-Phase Capabilities

Likely later-phase capabilities:

- richer autonomous implementation
- advanced specialist agents
- release orchestration
- broader policy enforcement
- deep deployment automation
- multi-user collaboration
- enterprise-grade RBAC
- plugin marketplace

These should not block the initial foundation.

---

## 16. Deliverables Requested from Claude Code

Claude Code should use this proposal to generate and scaffold a serious initial project foundation.

At minimum, it should create:

### Product / Planning Docs
- full PRD
- roadmap
- milestone plan
- backlog
- workflows
- ADRs
- architecture overview

### Technical Design Docs
- component architecture
- domain model
- data model
- memory strategy
- orchestration strategy
- runtime model
- message protocol
- integration design

### Repo Scaffolding
- README
- docs structure
- source layout
- package/module layout
- starter application skeleton
- config/environment templates
- testing structure
- issue templates
- contributor documentation

### Developer Experience Assets
- local dev instructions
- examples
- architecture diagrams
- setup checklist
- initial task breakdown

---

## 17. Open Design Areas Claude Code Should Resolve

Claude Code should make specific recommendations for:

- implementation stack
- orchestration approach
- runtime model
- memory model
- domain model
- message protocol
- architecture graph representation
- tracker abstraction model
- repo lifecycle model
- MVP boundaries
- repo structure and scaffolding

Where multiple options exist, it should document tradeoffs and recommend one.

---

## 18. Success Criteria for Initial Output

The first Claude Code pass should leave the repo in a state where:

- the project vision is clearly documented
- the architecture direction is clear
- the domain model is defined
- initial implementation structure exists
- onboarding is straightforward
- the backlog is organized
- the project can immediately begin active development

---

## 19. Immediate Next Step

Claude Code should treat this document as the initial product handoff and expand it into:

- a detailed PRD
- a technical architecture
- domain model docs
- repo scaffolding
- an implementation roadmap
- initial GitHub-ready project assets
