package com.asif.sdet.banking.db;

import com.asif.sdet.banking.exception.FrameworkException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager implements DatabaseOperations {

    private final Connection connection;

    public DatabaseManager(String jdbcUrl, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new FrameworkException("Unable to connect to database: " + jdbcUrl, e);
        }
    }

    @Override
    public int executeUpdate(String sql, Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            bindParameters(statement, parameters);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new FrameworkException("Database update failed: " + sql, e);
        }
    }

    @Override
    public List<Map<String, Object>> executeQuery(String sql, Object... parameters) {
        List<Map<String, Object>> rows = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            bindParameters(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metadata = resultSet.getMetaData();
                while (resultSet.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int column = 1; column <= metadata.getColumnCount(); column++) {
                        row.put(metadata.getColumnLabel(column), resultSet.getObject(column));
                    }
                    rows.add(row);
                }
            }
            return rows;
        } catch (SQLException e) {
            throw new FrameworkException("Database query failed: " + sql, e);
        }
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        List<Map<String, Object>> rows = executeQuery(
                "SELECT balance FROM accounts WHERE account_number = ?", accountNumber);
        if (rows.isEmpty()) {
            throw new FrameworkException("Account not found: " + accountNumber);
        }
        Object value = rows.get(0).values().iterator().next();
        return new BigDecimal(value.toString());
    }

    public Connection getConnection() {
        return connection;
    }

    private void bindParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    @Override
    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new FrameworkException("Unable to close database connection", e);
        }
    }
}
