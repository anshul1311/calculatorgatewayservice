package com.noonpay.calculatorgatewayservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Operation {
    @Id
    private String operation;
    private double costOfOp;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getCostOfOp() {
        return costOfOp;
    }

    public void setCostOfOp(double costOfOp) {
        this.costOfOp = costOfOp;
    }

}
