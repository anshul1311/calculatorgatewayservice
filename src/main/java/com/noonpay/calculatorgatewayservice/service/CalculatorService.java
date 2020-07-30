package com.noonpay.calculatorgatewayservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noonpay.calculatorgatewayservice.entity.User;
import com.noonpay.calculatorgatewayservice.repository.UserRepository;
import com.noonpay.calculatorgatewayservice.pojos.AddResponsePojo;
import com.noonpay.calculatorgatewayservice.pojos.ErrorResponse;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import com.noonpay.calculatorgatewayservice.redis.RedisEntity;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@Service
public class CalculatorService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;
    @Value("${add.service.url}")
    String addUrl;
    @Value("${add.service.port}")
    String addPort;
    @Value("${add.service.path}")
    String addPath;
    @Value("${subtract.service.url}")
    String subtractUrl;
    @Value("${subtract.service.port}")
    String subtractPort;
    @Value("${subtract.service.path}")
    String subtractPath;
    @Value("${multiply.service.url}")
    String multiplyUrl;
    @Value("${divide.service.url}")
    String divideUrl;
    @Value("${multiply.service.port}")
    String multiplyPort;
    @Value("${divide.service.port}")
    String dividePort;
    @Value("${multiply.service.path}")
    String multiplyPath;
    @Value("${divide.service.path}")
    String dividePath;
    @Value("${kafka.service.path}")
    String kafkaPath;
    @Value("${server.port}")
    String port;

    @Autowired
    ErrorResponse errorResponse;
    @Autowired
    AddResponsePojo addResponsePojo;
    @Autowired
    TxnHistory txnHistory;
    @Autowired
    RedisTemplate<String, String> redisTemplate;


    public CalculatorService() {
    }

    public ResponseEntity<?> calculate(RequestPojo requestPojo) throws NotFoundException {
        Optional<User> userResult = userRepository.findById(requestPojo.getId());

        if (!userResult.isPresent()) {
            return errorResponse("user not found","user not found");

        }else if(performOperation(requestPojo).getBody() instanceof ErrorResponse){
                return performOperation(requestPojo);
        }else{


            if (performOperation(requestPojo) == null) {
                //ResponseEntity.badRequest().body(ErrorResponse.class);
                errorResponse("response cannot be null", "response cannot be null");

            }


            if (performOperation(requestPojo).getBody().getClass()==(Double.class)) {
                ResponseEntity<Double> actualResponse = (ResponseEntity<Double>) performOperation(requestPojo);
                addResponsePojo.setTotal(actualResponse.getBody().doubleValue());
                System.out.println("Value is" + addResponsePojo.getTotal());
                addResponsePojo.setStatus("Ok");
                txnHistory.setTxnHistory(requestPojo);
                RedisEntity user = new RedisEntity();
                user.setOperation(requestPojo.getOperation());
                user.setTotal(actualResponse.getBody().doubleValue());
                updateCacheRedis(requestPojo.getId(), user);
                ResponseEntity<String> str = restTemplate.postForEntity("http://localhost:" + port + kafkaPath + requestPojo.getOperation().toUpperCase() + " Txn is success" + addResponsePojo.getTotal(), null, String.class);
                System.out.print("Kafka Message sent" + str.getBody());
                return ResponseEntity.ok(addResponsePojo);

            } else {
               return errorResponse("not found", "not found");
            }
        }
    }

    public ResponseEntity<?> performOperation(RequestPojo requestPojo) throws NotFoundException {
        String url = "";


            switch (requestPojo.getOperation()) {
                case "add":
                    url = addUrl + ":" + addPort + addPath;
                    break;
                case "subtract":
                    url = subtractUrl + ":" + subtractPort + subtractPath;
                break;
                case "divide":
                    url = divideUrl + ":" + dividePort + dividePath;
                    break;
                case "multiply":
                    url = multiplyUrl + ":" + multiplyPort + multiplyPath;
                    break;
                default:
                    return errorResponse("operation not valid","operation not valid");
            }
            return restTemplate.getForEntity(url + "?value1=" + requestPojo.getValue1() + "&value2=" + requestPojo.getValue2(), Double.class);

        //return restTemplate.getForEntity(url + "?value1=" + requestPojo.getValue1() + "&value2=" + requestPojo.getValue2(), Double.class);

    }

    public ResponseEntity<ErrorResponse> errorResponse(String message,String description){
    ErrorResponse errorResponse=new ErrorResponse();
    errorResponse.setErrorMessage(message);
        errorResponse.setErrorDescription(description);
    return ResponseEntity.badRequest().body(errorResponse);
    }

    private boolean updateCacheRedis(Integer userid, RedisEntity userDetails) {


        ObjectMapper Obj = new ObjectMapper();

        try {

            String jsonStr = Obj.writeValueAsString(userDetails);


            System.out.println(jsonStr);
            redisTemplate.opsForValue().set("userid_" + userid, jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }
}
