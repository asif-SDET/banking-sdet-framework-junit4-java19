package com.asif.sdet.banking.api;

import com.asif.sdet.banking.model.CustomerResponse;
import com.asif.sdet.banking.model.TransferRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestAssuredBankingClient implements ApiClient {

    private final String baseUrl;

    public RestAssuredBankingClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Response getCustomer(int customerId) {
        return given()
                .baseUri(baseUrl)
                .accept(ContentType.JSON)
                .pathParam("customerId", customerId)
            .when()
                .get("/api/customers/{customerId}")
            .then()
                .extract().response();
    }

    @Override
    public Response createTransfer(TransferRequest request) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request)
            .when()
                .post("/api/transfers")
            .then()
                .extract().response();
    }

    @Override
    public CustomerResponse getCustomerAsPojo(int customerId) {
        return getCustomer(customerId).as(CustomerResponse.class);
    }
}
