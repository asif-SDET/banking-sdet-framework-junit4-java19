package com.asif.sdet.banking.support;

import com.asif.sdet.banking.db.DatabaseManager;

public final class DatabaseSeeder {

    private DatabaseSeeder() {
    }

    public static void seed(DatabaseManager db) {
        db.executeUpdate("CREATE TABLE customers (customer_id INT PRIMARY KEY, first_name VARCHAR(50), last_name VARCHAR(50))");
        db.executeUpdate("CREATE TABLE accounts (account_number VARCHAR(30) PRIMARY KEY, customer_id INT, account_type VARCHAR(20), balance DECIMAL(15,2))");

        db.executeUpdate("INSERT INTO customers(customer_id, first_name, last_name) VALUES (?, ?, ?)", 1001, "Asif", "Aliyev");
        db.executeUpdate("INSERT INTO accounts(account_number, customer_id, account_type, balance) VALUES (?, ?, ?, ?)", "CHK-1001", 1001, "CHECKING", 1500.00);
        db.executeUpdate("INSERT INTO accounts(account_number, customer_id, account_type, balance) VALUES (?, ?, ?, ?)", "SAV-1001", 1001, "SAVINGS", 3000.00);
    }
}
