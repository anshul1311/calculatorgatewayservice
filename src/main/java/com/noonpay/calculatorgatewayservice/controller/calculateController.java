package com.noonpay.calculatorgatewayservice.controller;

import com.noonpay.calculatorgatewayservice.pojos.AddResponsePojo;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import com.noonpay.calculatorgatewayservice.service.CalculatorService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/noonpay")
public class calculateController {


    @Autowired
    CalculatorService additionService;




    @RequestMapping(value = "/calculate",method = RequestMethod.GET)
    public ResponseEntity<AddResponsePojo> addController(@RequestBody RequestPojo requestPojo) throws NotFoundException {
        return (ResponseEntity<AddResponsePojo>) additionService.calculate(requestPojo);
    }
}
