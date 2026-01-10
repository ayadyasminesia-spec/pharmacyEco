# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

PharmacyEcoYasmine is a **JHipster 8.11.0** monolithic e-commerce application for a pharmacy, built with:

- **Backend**: Spring Boot 3.4.5, Java 17, PostgreSQL, JWT authentication
- **Frontend**: React 19 + Vite + TailwindCSS (in `pharmacy-web/` directory)
- **Build Tool**: Maven
- **Cache**: Hazelcast
- **Database Migrations**: Liquibase
- **i18n**: French (default) and English

## Essential Commands

### Development

Start the backend in dev mode (includes hot reload):

```bash
./mvnw
```

Start the frontend dev server:

```bash
cd pharmacy-web
npm run dev
```

### Testing

Run all backend tests:

```bash
./mvnw verify
```

Run backend unit tests only:

```bash
npm run backend:unit:test
```

### Building

Build production JAR:

```bash
./mvnw -Pprod clean verify
```

Build production WAR:

```bash
./mvnw -Pprod,war clean verify
```

### Docker

Start PostgreSQL database:

```bash
docker compose -f src/main/docker/postgresql.yml up --wait
# or use npm script
npm run docker:db:up
```

Stop and remove database:

```bash
npm run docker:db:down
```

Build Docker image:

```bash
npm run java:docker
# For ARM64 (Apple Silicon):
npm run java:docker:arm64
```

Start all required services:

```bash
npm run services:up
```

### Code Quality

Format code (Prettier):

```bash
npm run prettier:format
```

Check formatting:

```bash
npm run prettier:check
```

Run Sonar analysis (requires Sonar server running):

```bash
docker compose -f src/main/docker/sonar.yml up -d
./mvnw -Pprod clean verify sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

## Architecture

### Backend Structure

The backend follows standard JHipster conventions with package structure at `src/main/java/com/ayadyasmine/pharmacyecom/`:

- **`domain/`**: JPA entities representing the data model
- **`repository/`**: Spring Data JPA repositories
- **`service/`**: Service layer with business logic
- **`web/rest/`**: REST controllers (endpoints)
- **`config/`**: Spring configuration classes
- **`security/`**: JWT authentication and authorization
- **`aop/`**: Aspect-oriented programming (logging aspects)

All entities use DTOs (MapStruct) and are defined in `.jhipster/*.json` files. The entity relationships are defined in `pharmacy.jdl` (JHipster Domain Language).

### Domain Model (from pharmacy.jdl)

The application models a complete e-commerce pharmacy with:

**Core entities**: Product, ProductVariant, ProductImage, Category, Brand
**Customer management**: Customer (linked to User), Address
**Shopping flow**: Cart, CartItem, Order, OrderItem, OrderStatusHistory
**Content**: Review, Promotion, Article

**Key relationships**:

- Customer has one-to-one relationship with Cart and User
- Orders track status changes via OrderStatusHistory for audit trail
- Products support variants (different sizes/forms) via ProductVariant
- Many-to-many relationship between Promotion and Product

**Enums**:

- OrderStatus: PENDING, CONFIRMED, PAID, SHIPPED, DELIVERED, CANCELLED, RETURNED
- PaymentMethod: CASH_ON_DELIVERY, CIB, EDAHABIA

### Frontend Structure

Located in `pharmacy-web/`:

- Built with React 19 + Vite
- Styling with TailwindCSS v4
- Separated from backend (can be served independently)

### Database

- **Dev**: PostgreSQL (via Docker)
- **Prod**: PostgreSQL
- **Migrations**: Managed by Liquibase in `src/main/resources/config/liquibase/`
- Fake data for testing available in `src/main/resources/config/liquibase/fake-data/`

### Configuration

Configuration files follow Spring Boot profiles:

- `application.yml`: Base configuration
- `application-dev.yml`: Development profile
- `application-prod.yml`: Production profile

Management endpoints available at `/management` (secured for admin role).

## JHipster-Specific Notes

### Entity Generation

Entities are defined in `.jhipster/*.json` or can be modeled in `pharmacy.jdl`. To regenerate entities after modifications:

```bash
jhipster entity <EntityName>
```

### Profiles

The application uses Spring profiles:

- `dev`: Development mode with hot reload, H2 console, etc.
- `prod`: Production mode with optimizations
- `api-docs`: Enables SpringDoc OpenAPI documentation

Activate profiles via `SPRING_PROFILES_ACTIVE` environment variable or `--spring.profiles.active=dev,prod`.

### Security

- JWT-based authentication (tokens stored in `jwtSecretKey` in `.yo-rc.json`)
- Default roles: ROLE_USER, ROLE_ADMIN
- Security configuration in `SecurityConfiguration.java`
- Public endpoints can be configured in `SecurityConfiguration`

### Cache

Hazelcast is configured for second-level Hibernate cache. Cache configuration in `CacheConfiguration.java`.

## Important Files

- **`pharmacy.jdl`**: Entity model definition (source of truth for domain)
- **`.yo-rc.json`**: JHipster generator configuration
- **`pom.xml`**: Maven dependencies and build configuration
- **`src/main/resources/config/application*.yml`**: Spring Boot configuration
- **`checkstyle.xml`**: Code style rules
- **`.prettierrc`**: Code formatting rules
- **`.husky/`**: Git hooks (pre-commit formatting)

## Development Workflow

1. Database schema changes: Modify `pharmacy.jdl` → regenerate entities → review Liquibase changelogs
2. API changes: Modify entity JSON files → regenerate → implement service logic → update controllers
3. Frontend changes: Work in `pharmacy-web/` directory independently
4. Always run `npm run prettier:format` before committing (enforced by Husky hook)
