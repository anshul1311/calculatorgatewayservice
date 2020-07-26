package com.noonpay.calculatorgatewayservice.service;

import com.noonpay.calculatorgatewayservice.db.Transaction;
import com.noonpay.calculatorgatewayservice.db.TxnRepository;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TxnHistory {
    @Autowired
    TxnRepository txnRepository;

public void setTxnHistory(RequestPojo requestPojo) {
    Transaction transaction = new Transaction();
    transaction.setCreditsRemaining(txnRepository.getCredits(requestPojo.getId()) - 1);
    transaction.setCreditsUsed(1.0);
    transaction.setId(1);
    transaction.setTxnType("add");
    transaction.setTxnStatus("SUCCESS");
    txnRepository.save(transaction);
}
}
