# Architecture - Project Hub Service

## 1) System Context

This service is the **core business API** for Projects, Owners, Addresses, and Files.
It is consumed by the Angular frontend and secured with JWT authentication.

```
Angular UI  --->  Project Hub API  --->  Database
   |                    |
   |                    +-- JWT Auth (login + token validation)
   |
   +-- (optional) Security Service for /me and /permissions
```

## 2) Layered Architecture

- **Controllers**: HTTP endpoints and request validation
- **Services**: business rules and orchestration
- **Repositories**: persistence via Spring Data JPA
- **Domain**: JPA entities and relationships

This structure keeps business logic out of controllers and database details out of services.

## 3) Key Business Flows

- **CRUD Operations**: Projects, Owners, Addresses, Files
- **Relationships**:
  - Projects ↔ Owners (many-to-many)
  - Projects ↔ Address (one-to-one)
  - Projects ↔ Files (one-to-many)
  - Owners ↔ Address (one-to-one)

## 4) Authentication Model

- `POST /api/auth/login` returns a JWT.
- All `/api/**` endpoints require a valid Bearer token.
- Default dev user is created on startup via bootstrap logic.

## 5) Integration Points

- **Angular Frontend**
  - Calls `/api/**` for CRUD and relationships
  - Uses dev proxy to `http://localhost:8080`

- **Security Service (Optional)**
  - Not currently called from this code
  - Can be used for centralized permission resolution

## 6) Deployment View

Typical local/dev ports:

- Project Hub API: `http://localhost:8080`
- Security Service: `http://localhost:8081`
- Angular UI: `http://localhost:4200`

## 7) Key Source Files

- `src/main/java/pexper/projects/project_hub/controllers/`
- `src/main/java/pexper/projects/project_hub/services/`
- `src/main/java/pexper/projects/project_hub/domain/`
- `src/main/java/pexper/projects/project_hub/security/`
