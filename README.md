# grails-data-access

Sample app for **Data Access with GORM in Grails 8** (Apache Grails `8.0.0-SNAPSHOT`, JDK 21).

The guide walks through the core GORM skills you use on every real Grails app: modeling domains and associations, keeping query logic in services (criteria, `where` queries, and dynamic finders), exposing REST endpoints with JSON views, and locking it down with Spock unit and integration tests. GORM sits on Hibernate and Spring's transaction manager, so the same patterns apply whether you query in a service or persist from a controller.

## Layout

| Directory | What it is |
|-----------|------------|
| `initial/` | Vanilla Grails 8 REST API starter from [start.grails.org](https://start.grails.org) (`postgres`, `testcontainers`, `spock`). Work through the guide starting here. |
| `complete/` | The fully wired sample — `Author`, `Book`, and `Tag` domains with `belongsTo` / `hasMany` / many-to-many, `BookService` and `BookQueryService`, REST controllers, JSON views, Liquibase changelog, and Spock specs. |

## Running

```bash
git clone -b grails8 https://github.com/grails-guides/grails-data-access.git
cd grails-data-access/complete
./gradlew test integrationTest
```

To follow the guide step by step, start from `initial/`:

```bash
cd grails-data-access/initial
./gradlew test
```

Run the finished app:

```bash
cd grails-data-access/complete
./gradlew bootRun
```

Example endpoints: `GET /api/authors`, `GET /api/books`, `GET /api/books/search?q=data`, `GET /api/books/byAuthor/1`.

The integration spec (`BookDataAccessIntegrationSpec`) asserts that criteria, HQL, and `where` query logic in `BookService` and `BookQueryService` runs against a **real PostgreSQL database** (Testcontainers), not an in-memory mock.

Unit specs under `src/test/groovy/` cover domain constraints, service queries, and controller behaviour with `DataTest` / `ControllerUnitTest`.

## Requirements

- **JDK 21** (Temurin recommended; the Gradle build enforces Java 21+)
- **Docker** running locally — required for `integrationTest` (Testcontainers PostgreSQL). Unit tests (`./gradlew test`) do not need Docker.
- **PostgreSQL** on `localhost:5432` — required for `./gradlew bootRun` (default database `devDb` in `application.yml`)

If Gradle reports *"Run this build using a Java 21 or newer JVM"*, your shell or IDE is still on an older JDK:

```bash
sdk install java 21.0.6-tem
sdk default java 21.0.6-tem
java -version   # should show 21.x
```

If `integrationTest` fails with a Testcontainers / `docker.sock` error, start Docker Desktop (or your local Docker daemon) and retry.

## The pattern, in one place

```groovy
// 1. Model associations on the domain:
class Book {
    Author author
    static belongsTo = [author: Author]
    static hasMany = [tags: Tag]
}

// 2. Keep queries in a @Transactional service — criteria across an association:
@ReadOnly
List<Book> findBooksByAuthorName(String authorName) {
    Book.createCriteria().list {
        author { ilike('name', "%${authorName}%") }
        order('title')
    }
}

// 3. Or use type-safe where queries for filters:
Book.where { price >= minPrice }.list(sort: 'price', order: 'desc')
```

Controllers stay thin and delegate to services; JSON views under `grails-app/views/` shape REST responses.

## Guide prose

Published narrative lives on [grails.apache.org/guides](https://grails.apache.org/guides/) in [apache/grails-static-website](https://github.com/apache/grails-static-website) under `guides/grails-data-access/v8/`.

## CI

GitHub Actions (`.github/workflows/grails8.yml`) runs `./gradlew test` for `initial` and `complete`, and `./gradlew integrationTest` for `complete`, on pushes and PRs to the `grails8` branch.
