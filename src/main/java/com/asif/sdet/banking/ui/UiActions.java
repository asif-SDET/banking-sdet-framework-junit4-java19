package com.asif.sdet.banking.ui;

import org.openqa.selenium.By;

public interface UiActions {
    void click(By locator);
    void type(By locator, String text);
    String text(By locator);
    boolean isDisplayed(By locator);
}
