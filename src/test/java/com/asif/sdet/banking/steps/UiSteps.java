package com.asif.sdet.banking.steps;

import com.asif.sdet.banking.driver.DriverManager;
import com.asif.sdet.banking.page.AccountsPage;
import com.asif.sdet.banking.page.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class UiSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private AccountsPage accountsPage;

    @Given("I open the local business banking login page")
    public void openLocalBankingPage() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        accountsPage = new AccountsPage(driver);

        URL resource = UiSteps.class.getClassLoader().getResource("test-site/banking-login.html");
        if (resource == null) {
            throw new IllegalStateException("Local banking HTML test page was not found");
        }
        driver.get(resource.toExternalForm());
    }

    @When("I login with username {string} and password {string}")
    public void login(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("I should see the welcome message {string}")
    public void verifyWelcome(String expected) {
        assertEquals(expected, accountsPage.getWelcomeMessage());
    }

    @Then("I should see {int} banking accounts")
    public void verifyAccountCount(int expected) {
        assertEquals(expected, accountsPage.getAccountCount());
    }

    @Then("the page title should be {string}")
    public void verifyTitle(String expected) {
        assertEquals(expected, accountsPage.getPageTitle());
    }
}
