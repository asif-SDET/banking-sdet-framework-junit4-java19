package com.asif.sdet.banking.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginButton = By.id("loginButton");
    private final By errorMessage = By.id("errorMessage");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
    }

    public String getErrorMessage() {
        return text(errorMessage);
    }
}
