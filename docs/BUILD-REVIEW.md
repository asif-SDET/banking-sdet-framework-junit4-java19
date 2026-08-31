# Build review performed before packaging

This rebuild was intentionally simplified after the earlier framework had dependency/framework problems.

## What was changed

- Removed JUnit 5 entirely.
- Removed `cucumber-junit-platform-engine`.
- Removed JUnit Platform runner annotations.
- Removed PicoContainer dependency injection.
- Replaced the runner with classic JUnit 4 `@RunWith(Cucumber.class)`.
- Replaced very recent/milestone dependency/plugin versions with conservative stable versions.
- Targeted Java release 19 explicitly.
- Default Maven run excludes Selenium UI so browser/driver setup cannot hide core framework problems.
- API tests use a localhost mock banking service instead of an external public API.
- JDBC tests use a fresh in-memory H2 database per Cucumber scenario.
- UI tests use a local HTML page instead of an external website.

## Checks performed in the build environment

1. `pom.xml` parsed as valid XML.
2. Every Java source file was type-checked with `javac --release 19` using lightweight API-compatible stubs for third-party libraries.
3. The dependency-free banking domain classes were compiled with Java 19 bytecode target.
4. A real domain transfer smoke program was executed and verified checking/savings balances.
5. All 23 Gherkin steps were checked and each matched exactly one Java step-definition expression.
6. No JUnit 5, Jupiter, JUnit Platform or `cucumber-junit-platform-engine` imports/dependencies are present.
7. ZIP archive integrity was checked after packaging.

## Important limitation

Apache Maven is not installed in the build sandbox used to package this project, so an actual `mvn clean test` with downloaded Maven Central dependencies could not be executed here. The project therefore includes `VERIFY_ON_WINDOWS.cmd` so the first real dependency-resolution/build test happens directly on the target Windows/JDK 19/Maven 3.9 environment.

If that script fails, the useful information is the **first Maven `[ERROR]` section**, not the last 30 lines of the stack trace.
