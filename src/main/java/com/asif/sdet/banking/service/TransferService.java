package com.asif.sdet.banking.service;

import com.asif.sdet.banking.domain.Account;

import java.math.BigDecimal;

public class TransferService implements BankingOperations {

    @Override
    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}
