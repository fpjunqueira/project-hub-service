# Project Hub Service - Deep Explanation

## 1) Business Purpose

Project Hub is a backend REST API for managing **Projects**, **Owners**, **Addresses**, and
**Files**. It provides CRUD endpoints, handles relationships between entities, and exposes a
secured API for use by the Angular frontend or other clients. The service is designed to be a
clean example of a layered Spring Boot architecture with JWT-based authentication.

## 2) High-Level Architecture

The service follows a classic Spring layered structure:

- **Controllers** (`controllers/`): HTTP endpoints, validation, and response handling.
- **Services** (`services/`): business logic and orchestration.
- **Repositories** (`repositories/`): persistence via Spring Data JPA.
- **Domain** (`domain/`): JPA entities and relationships.
- **DTOs** (`dto/`): API shapes where direct entity exposure is avoided.
- **Config** (`config/`): security, CORS, and OpenAPI configuration.
- **Bootstrap** (`bootstrap/`): local seed data and default admin user.

This structure keeps HTTP, business rules, and persistence concerns isolated.

## 3) Core Domain Model

The domain represents a hub of projects and their related entities:

- **Project**
  - Has a name.
  - **Many-to-many** with Owners.
  - **One-to-one** with Address.
  - **One-to-many** with Files.
- **Owner**
  - Has name and email.
  - **Many-to-many** with Projects.
  - **One-to-one** with Address.
- **Address**
  - Linked to either a Project or an Owner (one-to-one relationships).
- **File**
  - Linked to a Project (many-to-one).
- **AppUser**
  - Represents authenticated users.
  - Stores username, password, and role (USER/ADMIN).

These relationships define the core business flows: owners can be linked to many projects,
projects have addresses and files, and addresses can belong to either an owner or a project.

## 4) API Surface

Each aggregate has a controller with standard CRUD endpoints and relationship helpers:

- `/api/projects` (list, get, create, update, delete)
  - `/api/projects/{id}/owners`
  - `/api/projects/{id}/files`
  - `/api/projects/{id}/address`
- `/api/owners` (list, get, create, update, delete)
  - `/api/owners/{id}/projects`
  - `/api/owners/{id}/address`
- `/api/addresses` (list, get, create, update, delete)
  - `/api/addresses/{id}/owner`
  - `/api/addresses/{id}/project`
- `/api/files` (list, get, create, update, delete)
  - `/api/files/{id}/project`
- `/api/auth/login` (JWT login)

Controllers delegate to services, which in turn call repositories. This keeps controllers
thin and makes business rules testable and reusable.

## 5) Authentication and Security

The backend uses Spring Security with JWT:

- `POST /api/auth/login` authenticates and returns a JWT.
- All `/api/**` routes are protected.
- JWT is validated on every request.
- The Angular frontend uses the token as a Bearer token on API calls.

Default dev credentials are configured in `application.yml`:

```yaml
app:
  jwt:
    secret: change-me-to-a-long-random-secret
    expiration-minutes: 60
  auth:
    default-user: admin
    default-password: admin123
    default-role: ADMIN
```

`bootstrap/UserBootstrapData` creates the default user on startup if it does not exist.

## 6) Data Flow (Typical Request)

Example: create a project

1. Client sends `POST /api/projects` with JSON payload.
2. `ProjectsController` validates and forwards to `ProjectService`.
3. `ProjectServiceImpl` applies rules and saves via `ProjectRepository`.
4. JPA persists to the database and returns the entity.
5. Controller sends the response back to the client.

Authentication is enforced before the controller logic runs by the JWT filter.

## 7) Configuration and Profiles

The service supports multiple database profiles:

- **Default**: `h2` (in-memory database) for local development.
- **persist**: MySQL with data seeded from `db/mysql/data.sql`.
- **sqlite**: Optional profile (requires a custom `application-sqlite.yml`).

Key file:

```yaml
spring:
  application:
    name: project-hub
  profiles:
    active: h2
```

The `persist` profile reads MySQL settings from environment variables and auto-seeds data
for local/dev usage.

## 8) Operational Features

Spring Boot Actuator is included with health/info endpoints enabled by default. Swagger
OpenAPI is also enabled to document and test the API.

## 9) Deployment and Dev Workflow

The repo includes:

- **Dockerfile** and **docker-compose.yml** for local containerized runs.
- **Helm chart** under `helm/project-hub` for Kubernetes deployments.
- Helper scripts (`build-and-run.sh`, `rebuild.sh`) to build the backend and frontend together.

## 10) Integration With the Other Projects

The backend is consumed by the Angular app and can optionally integrate with the
security-service:

- **Angular frontend → Project Hub**: the UI calls `/api/**` endpoints for CRUD. In dev,
  `proxy.conf.json` forwards `/api` to `http://localhost:8080`.
- **Authentication**: current backend code provides `POST /api/auth/login` and validates
  its own JWT tokens. This is the contract described in this backend and its README.
- **Security-service**: there is no direct service-to-service call in the current code.
  If you want centralized authorization, Project Hub can be evolved to call
  `/permissions` from security-service or to validate IdP-issued JWTs directly.

This means the **runtime integration is HTTP-based**, with JWT passed by the client.

## 11) Key Source References

- `src/main/java/pexper/projects/project_hub/ProjectHubApplication.java`
- `src/main/java/pexper/projects/project_hub/controllers/`
- `src/main/java/pexper/projects/project_hub/services/`
- `src/main/java/pexper/projects/project_hub/domain/`
- `src/main/java/pexper/projects/project_hub/security/`
- `src/main/resources/application.yml`
