package com.asif.sdet.banking.hooks;

import com.asif.sdet.banking.api.RestAssuredBankingClient;
import com.asif.sdet.banking.db.DatabaseManager;
import com.asif.sdet.banking.driver.DriverManager;
import com.asif.sdet.banking.support.DatabaseSeeder;
import com.asif.sdet.banking.support.MockBankingServer;
import com.asif.sdet.banking.support.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.UUID;

public class Hooks {

    @Before
    public void beforeScenario() {
        ScenarioContext.reset();
        ScenarioContext context = ScenarioContext.get();

        String jdbcUrl = "jdbc:h2:mem:banking_" + UUID.randomUUID()
                + ";MODE=MSSQLServer;DB_CLOSE_DELAY=-1";
        DatabaseManager database = new DatabaseManager(jdbcUrl, "sa", "");
        DatabaseSeeder.seed(database);
        context.setDatabase(database);

        MockBankingServer server = new MockBankingServer(database);
        server.start();
        context.setServer(server);
        context.setApiClient(new RestAssuredBankingClient(server.baseUrl()));
    }

    @After
    public void afterScenario(Scenario scenario) {
        ScenarioContext context = ScenarioContext.get();

        if (scenario.isFailed() && DriverManager.hasDriver()) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure screenshot");
        }

        DriverManager.quitDriver();

        if (context.getServer() != null) {
            context.getServer().close();
        }
        if (context.getDatabase() != null) {
            context.getDatabase().close();
        }
        ScenarioContext.reset();
    }
}
