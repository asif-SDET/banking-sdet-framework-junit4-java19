package com.asif.sdet.banking.db;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DatabaseOperations extends AutoCloseable {
    int executeUpdate(String sql, Object... parameters);
    List<Map<String, Object>> executeQuery(String sql, Object... parameters);
    BigDecimal getAccountBalance(String accountNumber);
    @Override
    void close();
}
