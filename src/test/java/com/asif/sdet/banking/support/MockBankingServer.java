package com.asif.sdet.banking.support;

import com.asif.sdet.banking.db.DatabaseManager;
import com.asif.sdet.banking.model.CustomerResponse;
import com.asif.sdet.banking.model.TransferRequest;
import com.asif.sdet.banking.model.TransferResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockBankingServer implements AutoCloseable {

    private final DatabaseManager database;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private HttpServer server;

    public MockBankingServer(DatabaseManager database) {
        this.database = database;
    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/api/customers", this::handleCustomer);
            server.createContext("/api/transfers", this::handleTransfer);
            server.start();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to start local banking API", e);
        }
    }

    public String baseUrl() {
        return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    private void handleCustomer(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("message", "Method not allowed"));
            return;
        }

        String[] parts = exchange.getRequestURI().getPath().split("/");
        if (parts.length < 4) {
            sendJson(exchange, 400, Map.of("message", "Customer id is required"));
            return;
        }

        int customerId = Integer.parseInt(parts[3]);
        List<Map<String, Object>> rows = database.executeQuery(
                "SELECT customer_id, first_name, last_name FROM customers WHERE customer_id = ?", customerId);

        if (rows.isEmpty()) {
            sendJson(exchange, 404, Map.of("message", "Customer not found"));
            return;
        }

        Map<String, Object> row = rows.get(0);
        CustomerResponse response = new CustomerResponse(
                ((Number) value(row, "customer_id", "CUSTOMER_ID")).intValue(),
                String.valueOf(value(row, "first_name", "FIRST_NAME")),
                String.valueOf(value(row, "last_name", "LAST_NAME"))
        );
        sendJson(exchange, 200, response);
    }

    private void handleTransfer(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("message", "Method not allowed"));
            return;
        }

        TransferRequest request;
        try (InputStream body = exchange.getRequestBody()) {
            request = objectMapper.readValue(body, TransferRequest.class);
        }

        BigDecimal fromBalance = database.getAccountBalance(request.getFromAccount());
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            sendJson(exchange, 400, new TransferResponse(null, "REJECTED", "Amount must be positive"));
            return;
        }
        if (request.getAmount().compareTo(fromBalance) > 0) {
            sendJson(exchange, 422, new TransferResponse(null, "REJECTED", "Insufficient funds"));
            return;
        }

        database.executeUpdate("UPDATE accounts SET balance = balance - ? WHERE account_number = ?",
                request.getAmount(), request.getFromAccount());
        database.executeUpdate("UPDATE accounts SET balance = balance + ? WHERE account_number = ?",
                request.getAmount(), request.getToAccount());

        TransferResponse response = new TransferResponse(
                UUID.randomUUID().toString(), "COMPLETED", "Transfer completed successfully");
        sendJson(exchange, 201, response);
    }

    private Object value(Map<String, Object> row, String lower, String upper) {
        if (row.containsKey(lower)) {
            return row.get(lower);
        }
        return row.get(upper);
    }

    private void sendJson(HttpExchange exchange, int statusCode, Object body) throws IOException {
        byte[] bytes = objectMapper.writeValueAsString(body).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

    @Override
    public void close() {
        if (server != null) {
            server.stop(0);
        }
    }
}
