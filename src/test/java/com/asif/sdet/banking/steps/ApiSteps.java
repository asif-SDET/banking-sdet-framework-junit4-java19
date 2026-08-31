package com.asif.sdet.banking.steps;

import com.asif.sdet.banking.model.CustomerResponse;
import com.asif.sdet.banking.model.TransferRequest;
import com.asif.sdet.banking.model.TransferResponse;
import com.asif.sdet.banking.support.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ApiSteps {

    private final ScenarioContext context = ScenarioContext.get();

    @When("I send a GET request for customer {int}")
    public void getCustomer(int customerId) {
        context.setResponse(context.getApiClient().getCustomer(customerId));
        CustomerResponse customer = context.getResponse().as(CustomerResponse.class);
        context.put("customerPojo", customer);
    }

    @When("I POST a transfer of {int} dollars from {string} to {string}")
    public void postTransfer(int amount, String from, String to) {
        TransferRequest request = new TransferRequest(from, to, BigDecimal.valueOf(amount));
        context.setResponse(context.getApiClient().createTransfer(request));
        TransferResponse transferResponse = context.getResponse().as(TransferResponse.class);
        context.put("transferResponse", transferResponse);
    }

    @Then("the API status code should be {int}")
    public void statusCodeShouldBe(int expectedStatusCode) {
        assertEquals(expectedStatusCode, context.getResponse().statusCode());
    }

    @Then("the customer POJO first name should be {string}")
    public void customerFirstName(String expected) {
        CustomerResponse customer = context.get("customerPojo", CustomerResponse.class);
        assertEquals(expected, customer.getFirstName());
    }

    @Then("the transfer response status should be {string}")
    public void transferStatus(String expected) {
        TransferResponse response = context.get("transferResponse", TransferResponse.class);
        assertEquals(expected, response.getStatus());
    }
}
