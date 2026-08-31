# Interview walkthrough

A natural answer to “Tell me about your automation framework”:

> My framework is a Maven project written in Java. I use Cucumber with JUnit 4 as the BDD runner, Selenium WebDriver with Page Object Model for UI testing, REST Assured for API testing, and JDBC for backend database validation. I keep reusable code in utility and manager classes, configuration in a properties file, and separate UI, API and database responsibilities into packages. I also use Java OOP concepts in the framework: BasePage and Account are abstract classes, reusable capabilities are interfaces, page classes use inheritance, REST and database clients are accessed through interface references for polymorphism, and POJOs use encapsulation. Maven manages dependencies and lets me execute different suites by tags and profiles.

Follow-up mapping:

- **Why Maven?** Dependency management, standard project structure, repeatable commands, CI integration.
- **Why POM?** Locators and page behavior are separated from step definitions; less duplication and easier maintenance.
- **Why abstract BasePage?** Every page shares browser actions, but BasePage itself is not a real application page.
- **Why interfaces?** They define contracts such as API or DB operations without tying callers to one implementation.
- **Where is polymorphism?** `ApiClient client = new RestAssuredBankingClient(...)`, `Account account = new CheckingAccount(...)`, and Selenium `WebDriver driver = new ChromeDriver(...)`.
- **Where is encapsulation?** Private POJO/domain fields with getters/setters.
- **Where is overloading?** `BasePage.click(By)` and `BasePage.click(WebElement)`.
- **Where is overriding?** Savings and checking accounts implement withdrawal rules differently.
- **How do you test end-to-end?** A scenario can submit a transaction through API, validate the response, and query the database to verify persisted balances.
