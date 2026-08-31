package com.asif.sdet.banking.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Bank {

    public static final String COUNTRY = "Canada";
    private static int totalBanksCreated = 0;

    private final String name;
    private final List<Customer> customers = new ArrayList<>();

    public Bank(String name) {
        this.name = Objects.requireNonNull(name);
        totalBanksCreated++;
    }

    public String getName() {
        return name;
    }

    public void addCustomer(Customer customer) {
        customers.add(Objects.requireNonNull(customer));
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public static int getTotalBanksCreated() {
        return totalBanksCreated;
    }
}
