# Banking SDET Automation Framework — Java 19 + Maven 3.9 + JUnit 4

This is a **portfolio/interview-friendly Maven automation framework** designed around a fictional business-banking application. It intentionally uses the tools you have practiced: **Java, Selenium WebDriver, Page Object Model, Cucumber BDD, JUnit 4, REST Assured, JDBC/SQL, Maven, Git/GitHub, POJOs, interfaces, abstract classes, inheritance, polymorphism, encapsulation, constants and static variables**.

The framework is purposely self-contained for API and database tests: it starts a tiny local banking API and an in-memory H2 database. The UI test opens a local HTML banking page, so it does not depend on a public demo website.

## Compatibility target

This version was rebuilt specifically for this environment:

```text
Apache Maven 3.9.0
Java 19.0.1 (Oracle JDK 19)
Windows 10 x64
JUnit 4.13.2
```

There are **no JUnit 5 / Jupiter / JUnit Platform dependencies** in this project.

## Quickest Windows check

You can double-click `VERIFY_ON_WINDOWS.cmd`. It prints your Java/Maven versions and then runs the non-UI suite. After that passes, `RUN_UI_VISIBLE.cmd` runs the Selenium scenario with Chrome visible.

## 1. First run — prove Maven/JUnit/Cucumber/API/DB work

Open **Command Prompt** in the project root (the folder containing `pom.xml`) and run:

```bat
mvn -v
mvn clean test
```

The default Maven run intentionally excludes `@ui`. It should execute the JUnit 4 unit tests plus Cucumber API, database and domain scenarios without needing a browser.

Reports are generated here:

```text
target/cucumber-reports/cucumber.html
target/cucumber-reports/cucumber.json
```

## 2. Run one layer at a time

```bat
mvn clean test -Papi
mvn clean test -Pdb
mvn clean test -Pdomain
mvn clean test -Psmoke
```

## 3. Run the Selenium UI test

Chrome is the default browser:

```bat
mvn clean test -Pui
```

By default it runs headless. To watch the browser:

```bat
mvn clean test -Pui -Dheadless=false
```

Other supported browsers:

```bat
mvn clean test -Pui -Dbrowser=edge -Dheadless=false
mvn clean test -Pui -Dbrowser=firefox -Dheadless=false
```

Selenium Manager is built into Selenium 4 and will resolve the correct browser driver when possible. Chrome/Edge/Firefox must be installed on the machine.

## 4. Run absolutely everything

```bat
mvn clean test -Pall
```

If you are troubleshooting, **do not start with `-Pall`**. Prove the non-UI framework first with `mvn clean test`, then add UI.

## IntelliJ setup for your machine

1. Unzip the project.
2. IntelliJ → **File → Open** → select this project's `pom.xml` or root folder.
3. When IntelliJ asks, choose **Load Maven Project**.
4. **File → Project Structure → Project SDK → JDK 19**.
5. Project language level: **19**.
6. Settings → Build Tools → Maven → Maven home path: `C:\Users\asifa\apache-maven-3.9.0`.
7. Maven Runner JRE: **Project JDK (19)**.
8. Open Maven tool window and click **Reload All Maven Projects**.
9. Wait until dependencies finish downloading before judging red imports.
10. Run `mvn clean test` from IntelliJ Terminal first.

## Framework architecture

```text
src/main/java/com/asif/sdet/banking
├── api
│   ├── ApiClient.java                  # interface
│   └── RestAssuredBankingClient.java   # implementation
├── config
│   └── ConfigReader.java               # properties reader / singleton
├── constants
│   └── FrameworkConstants.java         # public static final constants
├── db
│   ├── DatabaseOperations.java         # interface
│   └── DatabaseManager.java            # JDBC implementation
├── domain
│   ├── Account.java                    # abstract class
│   ├── CheckingAccount.java            # inheritance + overriding
│   ├── SavingsAccount.java             # inheritance + overriding
│   ├── Customer.java                   # encapsulation
│   ├── Bank.java                       # collections + static variable
│   └── AccountType.java                # enum
├── driver
│   ├── BrowserType.java
│   ├── DriverFactory.java
│   └── DriverManager.java              # static ThreadLocal driver holder
├── exception
│   └── FrameworkException.java
├── model
│   ├── CustomerResponse.java            # API POJO
│   ├── TransferRequest.java             # API request POJO
│   └── TransferResponse.java            # API response POJO
├── page
│   ├── BasePage.java                   # abstract POM base page
│   ├── LoginPage.java
│   ├── AccountsPage.java
│   └── TestPage.java                   # requested practice page
├── service
│   ├── BankingOperations.java          # interface
│   └── TransferService.java            # polymorphic implementation
└── ui
    └── UiActions.java                  # interface

src/test/java/com/asif/sdet/banking
├── base/BaseTest.java                  # JUnit 4 @Before/@After example
├── hooks/Hooks.java                    # Cucumber @Before/@After
├── runner/RunCucumberTest.java         # classic JUnit 4 Cucumber runner
├── steps/...                           # BDD step definitions
├── support/...                         # local API + DB seed + context
└── unit/...                            # plain JUnit 4 tests
```

## JUnit 4 runner — the important difference

The runner is the classic form you already know:

```java
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.asif.sdet.banking",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        }
)
public class RunCucumberTest {
}
```

There is no `@Suite`, `@IncludeEngines`, `@SelectClasspathResource`, Jupiter, or JUnit Platform engine.

## What the framework demonstrates in interviews

### Encapsulation

`Customer`, `Account`, `TransferRequest` and other POJOs keep fields `private` and expose controlled getters/setters.

### Inheritance

`CheckingAccount` and `SavingsAccount` extend the abstract `Account` class. `LoginPage`, `AccountsPage`, and `TestPage` extend `BasePage`.

### Abstraction

`Account` and `BasePage` are abstract classes. `ApiClient`, `DatabaseOperations`, `UiActions`, and `BankingOperations` are interfaces.

### Polymorphism

Examples:

```java
Account account = new CheckingAccount(...);
ApiClient apiClient = new RestAssuredBankingClient(baseUrl);
WebDriver driver = new ChromeDriver(options);
```

### Overriding

`CheckingAccount.withdraw()` and `SavingsAccount.withdraw()` override the abstract behavior defined by `Account`.

### Overloading

`BasePage.click(By locator)` and `BasePage.click(WebElement element)` are overloaded methods.

### Static variables

`Bank.totalBanksCreated` is a shared static class variable. `DriverManager` uses a static `ThreadLocal<WebDriver>`.

### Constant variables

`FrameworkConstants` demonstrates `public static final` constants.

## UI layer

The local page is at:

```text
src/test/resources/test-site/banking-login.html
```

The test uses real Selenium WebDriver against this HTML page. It validates login, account count, page title and dashboard content using Page Objects.

## API layer

`MockBankingServer` starts on a random localhost port before API scenarios. REST Assured calls endpoints such as:

```text
GET  /api/customers/1001
POST /api/transfers
```

The POST example uses `TransferRequest` serialization and `TransferResponse` deserialization.

## Database layer

By default, Cucumber scenarios use an H2 in-memory database so they are runnable anywhere after Maven dependencies are downloaded. JDBC code still uses standard:

```text
Connection
PreparedStatement
ResultSet
```

For a real SQL Server, copy `config-sqlserver.properties.example`, use your environment values, and instantiate `DatabaseManager` with that JDBC URL. Do not commit real credentials.

## GitHub

From the project root:

```bat
git init
git add .
git commit -m "Add Java 19 JUnit4 banking SDET automation framework"
git branch -M main
git remote add origin YOUR_GITHUB_REPOSITORY_URL
git push -u origin main
```

## Troubleshooting order

If anything fails, run these in this exact order:

```bat
java -version
mvn -v
mvn clean
mvn -U test -Pdomain
mvn -U test -Pdb
mvn -U test -Papi
mvn -U test -Pui
```

Then send the **first Maven error**, starting from `[ERROR]`, rather than the final stack trace. The first error is normally the useful one.

See `docs/COMPATIBILITY-CHECKLIST.md` and `docs/FRAMEWORK-WALKTHROUGH.md` for a guided review.

## Build review

See `docs/BUILD-REVIEW.md` for exactly what was checked before this ZIP was created and the one limitation of the packaging environment.
