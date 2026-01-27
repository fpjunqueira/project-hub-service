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

## Building and Running

### Using Maven Wrapper

```bash
./mvnw spring-boot:run
```

On Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

To run with MySQL:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=persist
```

To re-seed MySQL data, drop the database (or truncate the tables) and restart with
`persist`, or edit `db/mysql/data.sql` and restart. If you want to disable seeding,
set `spring.sql.init.mode=never` in `application-persist.yml`.

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

### Using Docker Compose

```bash
docker-compose up --build
```

The compose stack starts MySQL and runs the API with the `persist` profile.

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

## API Documentation

OpenAPI/Swagger is enabled. When the service is running, visit:

- `http://localhost:8080/swagger-ui.html`
  or
- `http://localhost:8080/swagger-ui/index.html`

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

## Review

- Lambdas
- Records
- Threads/syncronized
- Actuator
- java versions
- microservices/patterns
- azure (cloud)
- interfaces funcionais (???)
- jdk, jvm, jrm

## Modules

`- Seção 11: MySql with Spring Boot` -> ADMIN
`- Seção 16: Paging and Sorting with Spring MVC`
`- Seção 23: Spring Authorization Server`
`- Seção 24: Spring MVC OAuth2 Resource Server`
- Seção 30: Spring Data MongDB
- Seção 37: Spring Cloud Gateway
`- Seção 44: Spring Boot Actuator`
- Seção 51: Kubernetes with Spring Boot
- Seção 53: Spring Boot Microservices with Apache Kafka

# TODO

- complement vi tests
- lambdas
- Records