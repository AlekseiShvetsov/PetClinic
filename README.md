# PetClinic REST API Tests

Test project for automated API testing of Spring PetClinic REST.

## Requirements

- Java 17+
- Maven 3.8+
- Docker

## Tested application

Start the application under test:

```bash
docker run --rm -p 9966:9966 springcommunity/spring-petclinic-rest
```

Application URL: http://localhost:9966/petclinic

## How to run tests

```bash
mvn clean test
```

### Override base URL

```bash
mvn clean test -DbaseUrl=http://localhost:9966/petclinic
```

## Test structure

| Test class | What it verifies |
|------------|------------------|
| `HealthCheckTest` | GET `/actuator/health` returns `status: UP` |
| `OwnerCrudTest` | Full CRUD flow for owner |
| `NegativeTest` | POST `/api/owners` with empty fields returns 400 |

## Allure report

```bash
mvn allure:report
```

Report: `target/site/allure-maven/index.html`

## Technologies

| Technology | Version |
|------------|---------|
| Java | 17 |
| Maven | 3.9.x |
| JUnit 5 | 5.10.0 |
| RestAssured | 5.3.2 |
| AssertJ | 3.24.2 |
| Allure | 2.24.0 |