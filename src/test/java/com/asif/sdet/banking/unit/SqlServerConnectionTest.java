package com.asif.sdet.banking.unit;

import com.asif.sdet.banking.db.DatabaseManager;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SqlServerConnectionTest {

    @Test
    public void testSqlServerConnection() throws IOException {

        Properties properties = new Properties();

        FileInputStream inputStream = new FileInputStream(
                "src/test/resources/config/config-sqlserver.properties"
        );

        properties.load(inputStream);

        String jdbcUrl = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        DatabaseManager db = new DatabaseManager(
                jdbcUrl,
                username,
                password
        );

        List<Map<String, Object>> rows =
                db.executeQuery(
                        "SELECT * FROM Customers WHERE customer_id = ?",
                        1001
                );

        System.out.println(rows);

        db.close();
    }
}