package com.noonpay.calculatorgatewayservice.controller;


import com.noonpay.calculatorgatewayservice.pojos.AddResponsePojo;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import com.noonpay.calculatorgatewayservice.service.AdditionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/noonpay")
public class subtractController {

    @RequestMapping(value = "/subtract",method = RequestMethod.GET)
    public AddResponsePojo subtractController(@RequestBody RequestPojo requestPojo){
        //return new AdditionService().hitAdditionService(requestPojo);
        return null;
    }
}
