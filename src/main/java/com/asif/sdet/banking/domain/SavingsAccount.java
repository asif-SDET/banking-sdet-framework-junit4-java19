package com.asif.sdet.banking.domain;

import java.math.BigDecimal;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountNumber, BigDecimal openingBalance) {
        super(accountNumber, AccountType.SAVINGS, openingBalance);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        validatePositiveAmount(amount);
        if (amount.compareTo(getBalance()) > 0) {
            throw new IllegalStateException("Savings account cannot be overdrawn");
        }
        setBalance(getBalance().subtract(amount));
    }
}
