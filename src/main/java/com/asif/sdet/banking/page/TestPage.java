package com.asif.sdet.banking.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestPage extends BasePage {

    private final By pageHeader = By.tagName("h1");
    private final By statusBanner = By.id("statusBanner");

    public TestPage(WebDriver driver) {
        super(driver);
    }

    public String getHeader() {
        return text(pageHeader);
    }

    public boolean isStatusBannerDisplayed() {
        return isDisplayed(statusBanner);
    }
}
