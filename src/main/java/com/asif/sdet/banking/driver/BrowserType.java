package com.asif.sdet.banking.driver;

public enum BrowserType {
    CHROME,
    EDGE,
    FIREFOX;

    public static BrowserType from(String value) {
        return BrowserType.valueOf(value.trim().toUpperCase());
    }
}
