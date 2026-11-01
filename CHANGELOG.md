# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [Unreleased]

### Changed

- Depend on the released QQQ 0.40.0 instead of the unpublished 0.40.0-SNAPSHOT so CI resolves from Maven Central

### Added

- Gradle multi-module project structure (core, orchestration, integrations, memory, server)
- ConciliumServer with QQQ Material Dashboard admin UI
- ConciliumMetaDataProvider extending AbstractQQQApplication
- Docker dev stack (PostgreSQL 17, MinIO)
- Liquibase migration framework
- CircleCI/Munitor CI/CD configuration
- Next.js frontend placeholder with Tailwind CSS
- GitHub SDLC infrastructure (labels, milestones, issue templates, project board)
- Branch protection rulesets for main and develop
