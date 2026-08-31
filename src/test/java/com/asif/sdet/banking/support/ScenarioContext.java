package com.asif.sdet.banking.support;

import com.asif.sdet.banking.api.ApiClient;
import com.asif.sdet.banking.db.DatabaseManager;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public final class ScenarioContext {

    private static final ThreadLocal<ScenarioContext> CURRENT =
            ThreadLocal.withInitial(ScenarioContext::new);

    private final Map<String, Object> data = new HashMap<>();
    private DatabaseManager database;
    private MockBankingServer server;
    private ApiClient apiClient;
    private Response response;

    private ScenarioContext() {
    }

    public static ScenarioContext get() {
        return CURRENT.get();
    }

    public static void reset() {
        CURRENT.remove();
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        return (T) data.get(key);
    }

    public DatabaseManager getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseManager database) {
        this.database = database;
    }

    public MockBankingServer getServer() {
        return server;
    }

    public void setServer(MockBankingServer server) {
        this.server = server;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
