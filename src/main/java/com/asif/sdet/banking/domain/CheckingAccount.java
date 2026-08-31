package com.asif.sdet.banking.domain;

import java.math.BigDecimal;

public class CheckingAccount extends Account {

    private final BigDecimal overdraftLimit;

    public CheckingAccount(String accountNumber, BigDecimal openingBalance) {
        this(accountNumber, openingBalance, BigDecimal.ZERO);
    }

    public CheckingAccount(String accountNumber, BigDecimal openingBalance, BigDecimal overdraftLimit) {
        super(accountNumber, AccountType.CHECKING, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        validatePositiveAmount(amount);
        BigDecimal available = getBalance().add(overdraftLimit);
        if (amount.compareTo(available) > 0) {
            throw new IllegalStateException("Insufficient checking-account funds");
        }
        setBalance(getBalance().subtract(amount));
    }
}
