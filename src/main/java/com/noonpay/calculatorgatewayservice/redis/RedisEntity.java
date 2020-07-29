package com.noonpay.calculatorgatewayservice.redis;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("RedisEntity")
public class RedisEntity {
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    private String operation;
    private Double total;


}
