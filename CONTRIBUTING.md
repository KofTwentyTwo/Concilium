# Contributing to Concilium

Contributions are welcome. This document covers the basics.

## Development Setup

See [README.md](README.md) for prerequisites and local development instructions.

## Workflow

1. Create a GitHub Issue (or pick an existing one) before starting work.
2. Create a feature branch: `feature/{issue-number}-{short-description}`
3. Make your changes, following the coding conventions below.
4. Ensure all tests pass: `./gradlew test`
5. Open a PR targeting `develop`. Reference the issue in the PR body.

## Coding Conventions

### Java (Backend)

- 3-space indentation
- Opening brace on the next line
- Wrapper types (`Integer`, `Boolean`, `Long`) over primitives
- Fluent-style setters (`.withX()`)
- Flower box comments for classes and methods
- 3 blank lines between methods
- `QLogger` with `LogPair` for structured logging, never string concatenation
- No wildcard imports

### TypeScript (Frontend)

- 2-space indentation
- Follow existing patterns in `frontend/src/`

### Commits

- Conventional commit format: `feat:`, `fix:`, `docs:`, `chore:`, `refactor:`, `test:`
- Subject line under 72 characters
- Reference issue numbers: `feat(#42): add agent execution engine`

## Testing

- Backend: `./gradlew test`
- Frontend: `cd frontend && npm test`
- All tests must pass before opening a PR.

## License

By contributing, you agree that your contributions will be licensed under the GPL v3 license.
