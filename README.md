# Project Hub Service

Project Hub is a Spring Boot REST service that manages Projects, Owners, Addresses, and Files.
It exposes CRUD endpoints for each domain, using a layered architecture that separates HTTP,
business logic, and persistence.

## Architecture Overview

The service follows a classic Spring layered design:

- **Controllers** (`controllers`) handle HTTP requests/responses and map routes to services.
- **Services** (`services`) implement business logic and orchestrate repository calls.
- **Repositories** (`repositories`) provide data access via Spring Data JPA.
- **Domain** (`domain`) contains JPA entities mapped to database tables.
- **DTOs** (`dto`) represent request/response shapes when needed.
- **Config** (`config`) holds application and API documentation configuration.

This keeps HTTP concerns (validation, status codes) separate from domain rules, and domain
rules separate from persistence details.

## Package Layout

`src/main/java/pexper/projects/project_hub`

- `bootstrap/` - seed data for local development.
- `config/` - CORS, web settings, and OpenAPI configuration.
- `controllers/` - REST controllers for each aggregate.
- `domain/` - JPA entities and relationships.
- `dto/` - DTOs used by controllers/services.
- `repositories/` - Spring Data interfaces for persistence.
- `services/` - service interfaces and implementations.
- `ProjectHubApplication.java` - application entry point.

## Layers and Responsibilities

### Controllers

Controllers map incoming HTTP requests to service methods. Each controller is focused on one
aggregate (`Project`, `Owner`, `Address`, `File`) and exposes standard CRUD operations. They
return DTOs or entities (depending on the endpoint) and handle status codes and validation
errors at the edge.

### Services

Services encapsulate business logic and transactional behavior. Each service implementation
coordinates repository calls, enforces invariants, and transforms data when needed.

### Repositories

Repositories are Spring Data JPA interfaces. They provide CRUD operations without custom SQL,
which keeps the persistence layer concise and testable.

### Domain

Entities are JPA-annotated classes that define how data is stored and related. They model the
core concepts of the system: projects, owners, addresses, and files.

## Configuration

- **`application.properties`** - app settings (database, server port, etc).
- **`OpenApiConfig`** - Swagger / OpenAPI setup.
- **`WebConfig`** - web and CORS configuration.

## Building and Running

### Using Maven Wrapper

```bash
./mvnw spring-boot:run
```

On Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

### Using Docker

```bash
docker build -t project-hub-service .
docker run -p 8080:8080 project-hub-service
```

### Using Docker Compose

```bash
docker-compose up --build
```

### Using Helper Shell Scripts

These scripts build both the API (this repo) and the Angular UI, then start the stack via
Docker Compose. They expect the Angular app to exist at `../angular/project-hub` relative to
this service.

```bash
chmod +x build-and-run.sh rebuild.sh
./build-and-run.sh
```

Use `rebuild.sh` when you want to force a fresh rebuild of images before starting:

```bash
./rebuild.sh
```

## API Documentation

OpenAPI/Swagger is enabled. When the service is running, visit:

- `http://localhost:8080/swagger-ui.html`
  or
- `http://localhost:8080/swagger-ui/index.html`

## Data Bootstrapping

`bootstrap/BootstrapData` can seed initial data for local development, making it easier to
work with the frontend right away.

## Development Notes

- Prefer placing validation and business rules in services instead of controllers.
- Keep repository interfaces simple; introduce custom queries only when needed.
- When adding new domains, follow the same package conventions:
  - `domain` entity
  - `repository` interface
  - `service` interface + `service` implementation
  - `controller` endpoints

## Tests

Run tests with:

```bash
./mvnw test
```
