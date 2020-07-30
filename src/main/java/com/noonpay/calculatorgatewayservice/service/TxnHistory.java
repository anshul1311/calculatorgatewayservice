package com.noonpay.calculatorgatewayservice.service;

import com.noonpay.calculatorgatewayservice.entity.Transaction;
import com.noonpay.calculatorgatewayservice.enums.TxnStatus;
import com.noonpay.calculatorgatewayservice.repository.TxnRepository;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import com.noonpay.calculatorgatewayservice.repository.UserRepository;
import com.noonpay.calculatorgatewayservice.repository.operationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TxnHistory {
    @Autowired
    TxnRepository txnRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    operationRepository operationRepository;

public void setTxnHistory(RequestPojo requestPojo) {
    Transaction transaction = new Transaction();
    transaction.setCreditsRemaining(txnRepository.getCredits(requestPojo.getId()) - operationRepository.findById(requestPojo.getOperation()).get().getCostOfOp());
    transaction.setCreditsUsed(operationRepository.findById(requestPojo.getOperation()).get().getCostOfOp());
    transaction.setId(1);
    transaction.setTxnType(requestPojo.getOperation());
    transaction.setTxnStatus(TxnStatus.SUCCESS);
    userRepository.updateCredits(txnRepository.getCredits(requestPojo.getId()) - operationRepository.findById(requestPojo.getOperation()).get().getCostOfOp(),requestPojo.getId());
    txnRepository.save(transaction);
}
}
