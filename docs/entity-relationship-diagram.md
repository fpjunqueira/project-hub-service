# Project Hub - Entity Relationship Diagram

This document describes the JPA entity relationships in the Project Hub service.

## Mermaid ER Diagram

```mermaid
erDiagram
    Project ||--o| Address : "has"
    Project }o--|| ContractRegistration : "belongs to"
    Project }o--o{ Owner : "has owners"
    Project ||--o{ File : "has"
    Project ||--o{ Billing : "has"
    Project ||--o{ Ticket : "has"
    Owner ||--o| Address : "has"
    Ticket ||--o{ TicketHistory : "references"

    Project {
        long id PK
        string projectName
        string projectType
        string projectNumber
        string purchaseOrder
        string serviceOrder
        string poNumber
        string directClient
        string directClientManager
        string finalClient
        string finalClientManager
        string siteId
        string addressId
    }

    Address {
        long id PK
        string street
        string city
        string state
        string number
        string zipCode
    }

    Owner {
        long id PK
        string name
        string email
    }

    ContractRegistration {
        long id PK
        string directClient
        string directClientManager
        string finalClient
        string finalClientManager
        string projectType
        string projectNumber
        string purchaseOrder
        string serviceOrder
        string poNumber
        string siteType
        string siteId
        string addressId
        string totalProjectValue
        string projectPhases
    }

    File {
        long id PK
        string filename
        string path
    }

    Billing {
        long id PK
        string legalName
        string taxId
        string billingAddress
        string phone
        string email
        string issueDate
        string dueDate
        string totalAmount
        string projectNumber
    }

    Ticket {
        long id PK
        string ticketNumber
        string status
        date dueDate
        timestamp createdAt
        string directClient
        string finalClient
        string projectNumber
        string address
        string activityDescription
    }

    TicketHistory {
        long id PK
        long ticketId FK
        string previousStatus
        string newStatus
        timestamp changedAt
        string changedBy
    }

    AppUser {
        long id PK
        string username UK
        string password
        enum role
    }

    Documentation {
        long id PK
        string documentationType
        string projectNumber
        string directClient
        string finalClient
    }

    ClaroSite {
        long id PK
        string siteId
        string addressId
        string name
    }

    TimSite {
        long id PK
        string siteId
        string addressId
        string elementType
        string technology
    }

    VivoSite {
        long id PK
        string sequence
        string addressId
        string name
        string state
    }

    UserRegistration {
        long id PK
        string fullName
        string cpf
        string username
    }

    VehicleRegistrationInfo {
        long id PK
        string licensePlate
        string renavam
        string vehicleType
    }
```

## Relationship Summary

| From | To | Type | Description |
|------|-----|------|-------------|
| Project | Address | One-to-One | Project has one address |
| Project | ContractRegistration | Many-to-One | Projects reference a contract |
| Project | Owner | Many-to-Many | Projects have many owners (via `owner_project` join table) |
| Project | File | One-to-Many | Project has many files |
| Project | Billing | One-to-Many | Project has many billings |
| Project | Ticket | One-to-Many | Project has many tickets |
| Owner | Address | One-to-One | Owner has one address |
| Ticket | TicketHistory | One-to-Many | Ticket status changes (via `ticketId` reference) |

## Standalone Entities

The following entities have no JPA relationships to other domain entities:

- **AppUser** – User authentication (role is an enum, not a separate entity)
- **Documentation** – Documentation records (reference fields are strings, not FKs)
- **ClaroSite** – Site lookup data
- **TimSite** – Site lookup data  
- **VivoSite** – Site lookup data
- **UserRegistration** – User/HR registration data
- **VehicleRegistrationInfo** – Vehicle fleet data

## Notes

- **Address** can be associated with either an **Owner** or a **Project** (via `address_id` on the owning side).
- **TicketHistory** references **Ticket** via `ticketId` (Long) rather than a JPA `@ManyToOne`; it is a logical relationship.
- **owner_project** is the join table for the Project ↔ Owner many-to-many relationship.
