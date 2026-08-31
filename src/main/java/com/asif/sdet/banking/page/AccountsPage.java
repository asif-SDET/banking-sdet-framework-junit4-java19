package com.asif.sdet.banking.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsPage extends BasePage {

    private final By welcomeMessage = By.id("welcomeMessage");
    private final By accountRows = By.cssSelector("#accountsTable tbody tr");
    private final By checkingBalance = By.id("checkingBalance");
    private final By savingsBalance = By.id("savingsBalance");

    public AccountsPage(WebDriver driver) {
        super(driver);
    }

    public String getWelcomeMessage() {
        return text(welcomeMessage);
    }

    public int getAccountCount() {
        return driver.findElements(accountRows).size();
    }

    public String getCheckingBalance() {
        return text(checkingBalance);
    }

    public String getSavingsBalance() {
        return text(savingsBalance);
    }
}
