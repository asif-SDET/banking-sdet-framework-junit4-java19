package com.asif.sdet.banking.unit;

import com.asif.sdet.banking.base.BaseTest;
import com.asif.sdet.banking.domain.Account;
import com.asif.sdet.banking.domain.CheckingAccount;
import com.asif.sdet.banking.domain.SavingsAccount;
import com.asif.sdet.banking.service.BankingOperations;
import com.asif.sdet.banking.service.TransferService;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DomainModelTest extends BaseTest {

    @Test
    public void shouldTransferBetweenPolymorphicAccounts() {
        Account checking = new CheckingAccount("CHK-JUNIT", new BigDecimal("1000.00"));
        Account savings = new SavingsAccount("SAV-JUNIT", new BigDecimal("500.00"));
        BankingOperations service = new TransferService();

        service.transfer(checking, savings, new BigDecimal("150.00"));

        assertEquals(0, checking.getBalance().compareTo(new BigDecimal("850.00")));
        assertEquals(0, savings.getBalance().compareTo(new BigDecimal("650.00")));
    }
}
