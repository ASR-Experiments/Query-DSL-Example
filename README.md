# Query DSL example with Springboot and Gradle

- Demonstrates how we can enable Query DSL in an Springboot Application with Gradle as a builder.

## Process

### Step 0: Boilerplate creation

#### Target

Setup for a basic JPA based Springboot application with all the required dependencies.

#### Mandatory steps

- Create a base project using [Spring initialzr](https://start.spring.io/)
    - `Spring Web` and `Spring Data JPA` is must.

#### Additional steps

- Added `Actuator`, `Lombok` and `Rest Repositories` for saving development time.

### Step 1: Setting up the base application

#### Target

Setup the entities and repositories which will be used to fetch the details from the Database.

#### Mandatory steps

- Add basic entities and controllers (here, handled by `Rest Repositories`)

#### Additional steps

- Added Springdoc for API's documentation and UI.

### Step 2: Implementing Query DSL

#### Target

Setup query DSL with one of the entity/repository which should be ready to be consumed by the API.

#### Mandatory Steps
- Add `query-dsl-jpa` amd `query-dsl-apt` dependencies.
- `query-dsl-apt` was not working with `lombok` annotation processor, so combined all of them into one.
- Implemented 2 more interfaces to support query dsl: `QuerydslPredicateExecutor<Country>` and `QuerydslBinderCustomizer<QCountry>` and the final code (class declaration) looks something like the following
  ```java
  // ...
  public interface CountryRepository extends
    JpaRepository<Country, UUID>,
    QuerydslPredicateExecutor<Country>,
    QuerydslBinderCustomizer<QCountry>
  // ...
  ```
  
### Additional steps
- Add customizer for Query DSL in Repository, to support fields from Abstract parent class. Eg:
  ```java
  @Override
  default void customize(QuerydslBindings bindings, QCountry root) {
    bindings.including(root.createdDate);
    bindings.including(root.lastModifiedDate);
    bindings.including(root.version);
    bindings.including(root.name);
    bindings.excluding(root.states);
  }
  ```

## Sonar

### Badges

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=ASR-Experiments_Query-DSL-Example)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=bugs)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)

### Stats

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=coverage)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=ASR-Experiments_Query-DSL-Example&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=ASR-Experiments_Query-DSL-Example)
****