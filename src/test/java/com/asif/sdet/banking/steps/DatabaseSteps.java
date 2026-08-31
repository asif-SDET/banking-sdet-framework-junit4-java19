package com.asif.sdet.banking.steps;

import com.asif.sdet.banking.support.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DatabaseSteps {

    private final ScenarioContext context = ScenarioContext.get();

    @When("I query the database for customer {int}")
    public void queryCustomer(int customerId) {
        List<Map<String, Object>> rows = context.getDatabase().executeQuery(
                "SELECT customer_id, first_name, last_name FROM customers WHERE customer_id = ?", customerId);
        assertFalse("Expected a customer row", rows.isEmpty());
        context.put("customerRow", rows.get(0));
    }

    @Then("the database customer first name should be {string}")
    public void verifyCustomerFirstName(String expected) {
        Map<?, ?> row = context.get("customerRow", Map.class);
        Object actual = row.containsKey("FIRST_NAME") ? row.get("FIRST_NAME") : row.get("first_name");
        assertEquals(expected, String.valueOf(actual));
    }

    @Given("account {string} has a starting database balance of {int} dollars")
    public void startingBalance(String accountNumber, int expected) {
        verifyDatabaseBalance(accountNumber, expected);
    }

    @Then("account {string} should have a database balance of {int} dollars")
    public void verifyDatabaseBalance(String accountNumber, int expected) {
        BigDecimal actual = context.getDatabase().getAccountBalance(accountNumber);
        assertEquals(0, actual.compareTo(BigDecimal.valueOf(expected)));
    }
}
