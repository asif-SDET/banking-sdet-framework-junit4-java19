package com.asif.sdet.banking.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Customer {

    private final int customerId;
    private String firstName;
    private String lastName;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(int customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(lastName);
    }

    public void addAccount(Account account) {
        accounts.add(Objects.requireNonNull(account));
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }
}
