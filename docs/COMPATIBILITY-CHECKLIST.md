# Compatibility / review checklist

Target environment:

- Windows 10 amd64
- Oracle JDK 19.0.1
- Maven 3.9.0
- JUnit 4.13.2 only

Framework review checklist:

- [x] POM uses `maven.compiler.release=19`.
- [x] No JUnit Jupiter dependency.
- [x] No JUnit Platform Suite dependency.
- [x] No Cucumber JUnit Platform Engine dependency.
- [x] Cucumber uses `cucumber-junit` and `@RunWith(Cucumber.class)`.
- [x] Surefire 3.2.5 is a stable release, not a milestone build.
- [x] API tests run against localhost rather than an external website.
- [x] Database tests use an in-memory H2 database by default.
- [x] Selenium tests use a local HTML file rather than an external website.
- [x] Default `mvn clean test` excludes UI to isolate browser/driver setup from the rest of the framework.
- [x] UI can be enabled separately with `-Pui`.
- [x] JDBC uses `PreparedStatement`.
- [x] REST Assured has GET and POST examples.
- [x] POJOs demonstrate serialization/deserialization.
- [x] POM contains abstract `BasePage`, `LoginPage`, `AccountsPage`, `TestPage`.
- [x] Domain model contains `Bank`, `Customer`, abstract `Account`, `CheckingAccount`, `SavingsAccount`.
- [x] Interfaces are used for UI/API/DB/service abstractions.
- [x] Static and `static final` variables are demonstrated.
- [x] JUnit 4 `@Before`, `@After`, `@Test` examples are present.
- [x] Cucumber hooks use `io.cucumber.java.Before/After` and are separate from JUnit 4 lifecycle annotations.

Important: this package is designed so API/DB/domain tests do not depend on Chrome. Selenium is deliberately isolated as a separate profile.
