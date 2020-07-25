package com.noonpay.calculatorgatewayservice.service;

import com.noonpay.calculatorgatewayservice.db.Transaction;
import com.noonpay.calculatorgatewayservice.db.TxnRepository;
import com.noonpay.calculatorgatewayservice.db.UserRepository;
import com.noonpay.calculatorgatewayservice.pojos.AddResponsePojo;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AdditionService {



    public AdditionService() {
    }

    public AddResponsePojo hitAdditionService(RequestPojo requestPojo,TxnRepository txnRepository) {

        RestTemplate restTemplate=new RestTemplate();
        String url ="http://localhost:8082/api/noonpay/addition?";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RequestPojo> request = new HttpEntity<>(new RequestPojo());
        ResponseEntity<Double> response=restTemplate.getForEntity(url+"value1="+requestPojo.getValue1()+"&value2="+requestPojo.getValue2(),Double.class);
        AddResponsePojo addResponsePojo=new AddResponsePojo();
        addResponsePojo.setTotal(response.getBody().doubleValue());
        System.out.println("Value is"+response.getBody().doubleValue());
        System.out.println("Value is"+addResponsePojo.getTotal());

        if(response.getStatusCode().value()==200){
            addResponsePojo.setStatus("Ok");
            Transaction transaction=new Transaction();
            transaction.setCreditsRemaining(txnRepository.getCredits(requestPojo.getId())-1);
            transaction.setCreditsUsed(1.0);
            transaction.setId(1);
            transaction.setTxnType("add");
            txnRepository.save(transaction);

        }
        return addResponsePojo;
    }
}
