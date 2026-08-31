package com.asif.sdet.banking.domain;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account {

    private final String accountNumber;
    private final AccountType accountType;
    private BigDecimal balance;

    protected Account(String accountNumber, AccountType accountType, BigDecimal openingBalance) {
        this.accountNumber = Objects.requireNonNull(accountNumber);
        this.accountType = Objects.requireNonNull(accountType);
        this.balance = Objects.requireNonNull(openingBalance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    protected void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void deposit(BigDecimal amount) {
        validatePositiveAmount(amount);
        balance = balance.add(amount);
    }

    public abstract void withdraw(BigDecimal amount);

    protected void validatePositiveAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
