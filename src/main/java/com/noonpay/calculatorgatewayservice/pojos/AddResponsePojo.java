package com.noonpay.calculatorgatewayservice.pojos;

import org.springframework.http.ResponseEntity;

public class AddResponsePojo {
    private String status;
    private double total;

    public AddResponsePojo(RequestPojo requestPojo) {
    }

    public AddResponsePojo() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
