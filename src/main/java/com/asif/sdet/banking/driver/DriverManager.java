package com.asif.sdet.banking.driver;

import com.asif.sdet.banking.config.ConfigReader;
import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            ConfigReader config = ConfigReader.getInstance();
            BrowserType browser = BrowserType.from(config.getOrDefault("browser", "chrome"));
            boolean headless = config.getBoolean("headless", true);
            DRIVER.set(DriverFactory.createDriver(browser, headless));
        }
        return DRIVER.get();
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }
}
