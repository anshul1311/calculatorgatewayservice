package com.noonpay.calculatorgatewayservice.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transaction {


    @Id
    @GeneratedValue
    private Integer id;
    private String txnType;
    private double creditsRemaining;
    private double creditsUsed;

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    private String txnStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public double getCreditsRemaining() {
        return creditsRemaining;
    }

    public void setCreditsRemaining(double creditsRemaining) {
        this.creditsRemaining = creditsRemaining;
    }

    public double getCreditsUsed() {
        return creditsUsed;
    }

    public void setCreditsUsed(double creditsUsed) {
        this.creditsUsed = creditsUsed;
    }



}
