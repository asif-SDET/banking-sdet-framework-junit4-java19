package com.asif.sdet.banking.service;

import com.asif.sdet.banking.domain.Account;

import java.math.BigDecimal;

public interface BankingOperations {
    void transfer(Account fromAccount, Account toAccount, BigDecimal amount);
}
