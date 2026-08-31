package com.asif.sdet.banking.steps;

import com.asif.sdet.banking.domain.Account;
import com.asif.sdet.banking.domain.CheckingAccount;
import com.asif.sdet.banking.domain.SavingsAccount;
import com.asif.sdet.banking.service.BankingOperations;
import com.asif.sdet.banking.service.TransferService;
import com.asif.sdet.banking.support.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DomainSteps {

    private final ScenarioContext context = ScenarioContext.get();

    @Given("a checking account has a balance of {int} dollars")
    public void checkingAccountHasBalance(int balance) {
        Account account = new CheckingAccount("CHK-DOMAIN", BigDecimal.valueOf(balance));
        context.put("checkingAccount", account);
    }

    @Given("a savings account has a balance of {int} dollars")
    public void savingsAccountHasBalance(int balance) {
        Account account = new SavingsAccount("SAV-DOMAIN", BigDecimal.valueOf(balance));
        context.put("savingsAccount", account);
    }

    @When("I transfer {int} dollars from checking to savings in the domain model")
    public void transferDomainMoney(int amount) {
        Account checking = context.get("checkingAccount", Account.class);
        Account savings = context.get("savingsAccount", Account.class);
        BankingOperations transferService = new TransferService();
        transferService.transfer(checking, savings, BigDecimal.valueOf(amount));
    }

    @Then("the checking domain balance should be {int} dollars")
    public void verifyCheckingBalance(int expected) {
        Account checking = context.get("checkingAccount", Account.class);
        assertEquals(0, checking.getBalance().compareTo(BigDecimal.valueOf(expected)));
    }

    @Then("the savings domain balance should be {int} dollars")
    public void verifySavingsBalance(int expected) {
        Account savings = context.get("savingsAccount", Account.class);
        assertEquals(0, savings.getBalance().compareTo(BigDecimal.valueOf(expected)));
    }
}
