package com.asif.sdet.banking.runner;

import io.cucumber.junit.CucumberOptions.SnippetType;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.asif.sdet.banking",
        tags = "@ui_read_usernames",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        snippets = SnippetType.CAMELCASE
)
public class RunCucumberTest {
    // JUnit 4 executes Cucumber through @RunWith(Cucumber.class).
}
