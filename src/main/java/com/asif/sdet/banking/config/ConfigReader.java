package com.asif.sdet.banking.config;

import com.asif.sdet.banking.exception.FrameworkException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final ConfigReader INSTANCE = new ConfigReader();
    private final Properties properties = new Properties();

    private ConfigReader() {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new FrameworkException("config/config.properties was not found on the classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new FrameworkException("Unable to load framework configuration", e);
        }
    }

    public static ConfigReader getInstance() {
        return INSTANCE;
    }

    public String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new FrameworkException("Missing configuration property: " + key);
        }
        return value.trim();
    }

    public String getOrDefault(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        return properties.getProperty(key, defaultValue).trim();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(getOrDefault(key, String.valueOf(defaultValue)));
    }
}
