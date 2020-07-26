package com.noonpay.calculatorgatewayservice.pojos;

import org.springframework.stereotype.Service;

@Service
public class ErrorResponse {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    private String errorDescription;
}
