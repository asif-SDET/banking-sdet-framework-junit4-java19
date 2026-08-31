package com.asif.sdet.banking.api;

import com.asif.sdet.banking.model.CustomerResponse;
import com.asif.sdet.banking.model.TransferRequest;
import io.restassured.response.Response;

public interface ApiClient {
    Response getCustomer(int customerId);
    Response createTransfer(TransferRequest request);
    CustomerResponse getCustomerAsPojo(int customerId);
}
