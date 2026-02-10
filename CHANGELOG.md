# Changelog

## Unreleased

### Added
- Added `SiteLookupController` with `/api/site-lookup` to resolve Claro/TIM/Vivo sites by `siteType` + `siteId` or `addressId`.
- Added `/api/contract-registrations/by-site` filtering by site type and site/address identifiers.
- Added `/api/projects/all`, `/api/projects/by-contract/{contractId}`, and `/api/projects/{id}/contract` endpoints.
- Added `/api/tickets/by-project/{projectId}` and `/api/billings/by-project/{projectId}` endpoints.
- Added repository finders for:
  - `ClaroSite` / `TimSite` / `VivoSite` lookups by `siteId` and `addressId`
  - `ContractRegistration` lookup by `siteType` + `siteId` or `addressId`
  - `Project` lookup by `contractRegistrationId`
  - `Ticket` / `Billing` lookup by `projectId`

### Changed
- Project creation and update now resolve and attach `ContractRegistration` by ID, and project update now persists additional contract-related fields.
- Ticket and Billing create/update now attach full `Project` entities when `project.id` is provided.
- Bootstrap data now:
  - Assigns `addressId` to Claro/TIM/Vivo sites
  - Sets `siteType` + site/address identifiers on contract registrations
  - Links projects to contract registrations and syncs contract fields into projects
  - Links tickets and billings to projects
