# Markdown Files Summary (Workspace Projects)

This file summarizes the content of each Markdown document previously listed across the
workspace projects.

## Angular Project Hub App

### `C:\git\angular\project-hub-app\architecture.md`
Summarizes the Angular UI architecture, auth model (MSAL), integration points with the
Project Hub API and Security Service, deployment ports, and key source files.

### `C:\git\angular\project-hub-app\explanation.md`
Deep dive into the Angular app: purpose, layered UI structure, routing, MSAL auth flow,
domain feature structure, data access services, and integration with backend services.

### `C:\git\angular\project-hub-app\README.md`
Frontend README covering project structure, routing, services, models, dev server usage,
login flow (JWT in README), build/test commands, and Angular CLI resources.

## Java Project Hub Service

### `C:\git\java\project-hub-service\architecture.md`
Architecture overview of the Project Hub API: layered design, domain relationships, JWT
login flow, integration points, deployment ports, and key package locations.

### `C:\git\java\project-hub-service\explanation.md`
Comprehensive service explanation: domain model, CRUD endpoints, JWT security, profiles
(h2/persist/sqlite), dev workflow, Docker/Helm, and integration with other projects.

### `C:\git\java\project-hub-service\README.md`
Main backend README covering layers, package layout, profiles, run commands, Docker usage,
API docs, JWT login details, actuator endpoints, and development notes.

### `C:\git\java\project-hub-service\HELP.md`
Spring Initializr generated help with Maven/Spring reference links and POM inheritance notes.

## Java Security Service

### `C:\git\java\security-service\architecture.md`
Architecture summary for the security-service: IdP integration, JWT validation, claims
mapping, permissions endpoint, integration with UI/API, and diagrams.

### `C:\git\java\security-service\README.md`
Overview of the security-service: OAuth2/OIDC rationale, endpoints, local run, config
snippets, and a detailed MSAL primer with diagrams and workspace context.

### `C:\git\java\security-service\MSAL_README.md`
Focused MSAL guide: definitions, token types, scopes, OAuth2/OIDC flows, differences from
OAuth2, Angular flow, and glossary.

### `C:\git\java\security-service\explanation.md`
Deep explanation of the security-service: JWT validation, claims-to-authorities mapping,
permission resolution, endpoints, configuration, and integration with other projects.

### `C:\git\java\security-service\docs\architecture.md`
Security platform architecture notes: OAuth2/OIDC flows, token strategy, role mapping, and
Azure IdP rationale.

### `C:\git\java\security-service\docs\learning-cronogram.md`
Five-week learning plan covering OAuth2/OIDC, MSAL, service-to-service security, containers,
Kubernetes, and Terraform.

### `C:\git\java\security-service\docs\plan-summary.md`
End-to-end security platform plan: goals, architecture, IdP options, service/app roles,
Terraform, CI/CD, Helm, and a detailed learning roadmap.

### `C:\git\java\security-service\docs\system-design.md`
System design document detailing components, flows, security dependencies, claims mapping,
deployment, CI/CD, and end-to-end integration.

### `C:\git\java\security-service\infra\README.md`
Terraform infrastructure README listing provisioned Azure resources, usage commands, and
key variables.
