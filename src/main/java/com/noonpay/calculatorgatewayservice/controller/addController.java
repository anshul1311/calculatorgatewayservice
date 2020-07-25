package com.noonpay.calculatorgatewayservice.controller;

import com.noonpay.calculatorgatewayservice.db.TxnRepository;
import com.noonpay.calculatorgatewayservice.db.UserRepository;
import com.noonpay.calculatorgatewayservice.pojos.AddResponsePojo;
import com.noonpay.calculatorgatewayservice.pojos.AdditionResponsePojo;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import com.noonpay.calculatorgatewayservice.service.AdditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/noonpay")
public class addController {
    @Autowired
    TxnRepository txnRepository;




    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public AddResponsePojo addController(@RequestBody RequestPojo requestPojo){
        return new AdditionService().hitAdditionService(requestPojo,txnRepository);
    }
}
