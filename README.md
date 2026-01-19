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

- **`application.yml`** - app settings (database, server port, etc).
- **`OpenApiConfig`** - Swagger / OpenAPI setup.
- **`WebConfig`** - web and CORS configuration.

## Prerequisites

- JDK 25 (see `pom.xml` `java.version`).
- Docker + Docker Compose for the MySQL stack (optional).

### Profiles

The default profile is `h2`, which uses an in-memory database for local runs.
Use the `persist` profile to connect to MySQL.

- `h2` uses `application-h2.yml`.
- `persist` uses `application-persist.yml` and reads MySQL settings from
  `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and
  `SPRING_DATASOURCE_PASSWORD` (with local defaults).

When the `persist` profile is active, the service loads seed data from
`src/main/resources/db/mysql/data.sql`. This runs on every startup with the
`persist` profile and is intended for local/dev usage.

SQLite dependencies are included (`sqlite-jdbc` and Hibernate community dialects).
To use SQLite, define a profile with a `jdbc:sqlite:` URL and set
`spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect`.

### Environment Variables (persist profile)

- `SPRING_DATASOURCE_URL` (defaults to localhost with `project_hub` database).
- `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD`.
- `SPRING_PROFILES_ACTIVE=persist` when running outside of Maven profile flags.

## Building and Running

### Using Maven Wrapper

```bash
./mvnw spring-boot:run
```

On Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

### Quickstart (H2)

1. Start the API with the default `h2` profile (commands above).
2. Login at `POST /api/auth/login` using `admin` / `admin123`.
3. Call a protected endpoint (example below).

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
```

To run with MySQL:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=persist
```

To re-seed MySQL data, drop the database (or truncate the tables) and restart with
`persist`, or edit `db/mysql/data.sql` and restart. If you want to disable seeding,
set `spring.sql.init.mode=never` in `application-persist.yml`.

### H2 Console

When running with the `h2` profile, the console is enabled at:

- `http://localhost:8080/h2-console`

Use:

- JDBC URL: `jdbc:h2:mem:projecthubdb`
- User: `sa`
- Password: (empty)

### SQLite (Optional)

Dependencies are already included. Create `application-sqlite.yml` with:

```yaml
spring:
  datasource:
    url: jdbc:sqlite:./project-hub.db
    driver-class-name: org.sqlite.JDBC
  jpa:
    database-platform: org.hibernate.community.dialect.SQLiteDialect
    hibernate:
      ddl-auto: update
```

Then run:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=sqlite
```

### Using Docker

```bash
docker build -t project-hub-service .
docker run -p 8080:8080 project-hub-service
```

To run with MySQL settings (same as `persist` profile):

```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=persist \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/project_hub \
  -e SPRING_DATASOURCE_USERNAME=project_hub \
  -e SPRING_DATASOURCE_PASSWORD=project_hub \
  project-hub-service
```

Notes:

- `host.docker.internal` works on Docker Desktop (Windows/macOS). On Linux, use the host IP.
- MySQL must be reachable and the `project_hub` database must exist.

### Using Docker Compose

```bash
docker-compose up --build
```

The compose stack starts MySQL and runs the API with the `persist` profile.

To stop and remove containers/volumes:

```bash
docker-compose down -v
```

### Accessing MySQL (phpMyAdmin)

When using Docker Compose, phpMyAdmin is available at:

- `http://localhost:8082/`

Login with:

- Server: `db`
- Username: `project_hub`
- Password: `project_hub`

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

### PowerShell Helper Script

On Windows, you can use:

```powershell
.\build-and-run.ps1
```

### Default Ports

- API: `8080`
- MySQL (Compose): `3306`
- phpMyAdmin (Compose): `8082`
- Angular dev server (if running separately): `4200`

## API Documentation

OpenAPI/Swagger is enabled. When the service is running, visit:

- `http://localhost:8080/swagger-ui.html`
  or
- `http://localhost:8080/swagger-ui/index.html`

## API Endpoints

All endpoints are prefixed with `/api` and require authentication unless noted.
Each aggregate exposes CRUD endpoints plus a paginated list and a full list.

- **Auth**: `POST /api/auth/login`
- **Projects**: `/api/projects`, `/api/projects/all`, `/api/projects/{id}`,
  `/api/projects/{id}/owners`, `/api/projects/{id}/files`, `/api/projects/{id}/address`
- **Owners**: `/api/owners`, `/api/owners/all`, `/api/owners/{id}`,
  `/api/owners/{id}/projects`, `/api/owners/{id}/address`
- **Addresses**: `/api/addresses`, `/api/addresses/all`, `/api/addresses/{id}`,
  `/api/addresses/{id}/owner`, `/api/addresses/{id}/project`
- **Files**: `/api/files`, `/api/files/all`, `/api/files/{id}`, `/api/files/{id}/project`

### Pagination

Paginated list endpoints accept:

- `page` (default `0`)
- `size` (default `10`)

Example:

```bash
curl -H "Authorization: Bearer <token>" \
  "http://localhost:8080/api/projects?page=0&size=20"
```

### CORS

By default, CORS allows the Angular dev server at `http://localhost:4200`
for `/api/**`. Update `WebConfig` if you need additional origins.

## Authentication (Spring Security + JWT)

All `/api/**` endpoints are protected. Clients must authenticate and send a Bearer token on
every request.

### Login Endpoint

`POST /api/auth/login`

Request body:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Response body:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresAt": "2026-01-18T23:10:00Z"
}
```

Use the token on all API calls:

```
Authorization: Bearer <token>
```

### Default User and JWT Settings

Update these in `application.yml` for local/dev:

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

## Frontend (Angular) Authentication Flow

The Angular app logs in, stores the token, and attaches it on every API call.

High level flow:

1. `LoginComponent` posts credentials to `/api/auth/login`.
2. `AuthService` stores the `token` (the app uses the `Bearer` scheme).
3. `AuthInterceptor` adds `Authorization: Bearer <token>` to all `/api/**` requests.

If the backend runs on a different port (example `8081`), update the Angular API base URL or
proxy to match the running backend URL.

## Actuator

Spring Boot Actuator is included to expose operational endpoints for health checks and
diagnostics.

### Default Endpoints

By default, only `health` and `info` are exposed over HTTP:

- `http://localhost:8080/actuator/health`
- `http://localhost:8080/actuator/info`

### Expose More Endpoints

To expose additional endpoints, add or update the following properties in
`application.yml` (or a profile-specific file like `application-h2.yml`):

```yaml
management:
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: health,info,metrics,env,loggers,threaddump
  endpoint:
    health:
      show-details: when_authorized
```

Notes:

- Use `management.endpoints.web.exposure.include=*` only in local/dev.
- Keep sensitive endpoints (`env`, `loggers`) restricted in production.
- `management.endpoint.health.show-details` can be `never`, `when_authorized`, or `always`.

### Quick Checks

Examples using `curl`:

```bash
curl http://localhost:8080/actuator
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/metrics
```

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

Run the unit test suite with:

```bash
./mvnw test
```

On Windows (PowerShell):

```powershell
.\mvnw.cmd test
```

### Test Coverage (JaCoCo)

Coverage reports are generated automatically when tests run. The HTML report is located at:

- `target/site/jacoco/index.html`

If you want to force a fresh report:

```bash
./mvnw clean test
```

On Windows (PowerShell):

```powershell
.\mvnw.cmd clean test
```

## Troubleshooting

- **401/403**: check the JWT secret and login credentials in `application.yml`.
- **CORS errors**: update `WebConfig` allowed origins to match your frontend URL.
- **MySQL connection**: ensure the database exists and the `persist` env vars are set.

## Data Model Summary

- **Owner** has one `Address` and many `Project`s (many-to-many).
- **Project** has one `Address`, many `Owner`s, and many `File`s.
- **Address** belongs to either one `Owner` or one `Project`.
- **File** belongs to a `Project` and is exposed as a DTO.
