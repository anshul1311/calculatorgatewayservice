package com.noonpay.calculatorgatewayservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noonpay.calculatorgatewayservice.db.Transaction;
import com.noonpay.calculatorgatewayservice.db.TxnRepository;
import com.noonpay.calculatorgatewayservice.db.User;
import com.noonpay.calculatorgatewayservice.db.UserRepository;
import com.noonpay.calculatorgatewayservice.pojos.AddResponsePojo;
import com.noonpay.calculatorgatewayservice.pojos.ErrorResponse;
import com.noonpay.calculatorgatewayservice.pojos.RequestPojo;
import com.noonpay.calculatorgatewayservice.redis.RedisEntity;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.Optional;

@Service
public class CalculatorService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TxnRepository txnRepository;

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
    @Autowired
    ErrorResponse errorResponse;
    @Autowired
    AddResponsePojo addResponsePojo;
    @Autowired
    TxnHistory txnHistory;
    @Autowired
     RedisTemplate<String,String> redisTemplate;


    public CalculatorService() {
    }

    public ResponseEntity<AddResponsePojo> hitAdditionService(RequestPojo requestPojo) throws NotFoundException {

        //String url ="http://localhost:8082/api/noonpay/addition?";
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //ResponseEntity<Double> response=restTemplate.getForEntity(url+"value1="+requestPojo.getValue1()+"&value2="+requestPojo.getValue2(),Double.class);
        ResponseEntity<Double> response=performOperation(requestPojo);

        if(response==null){
            //ResponseEntity.badRequest().body(ErrorResponse.class);
            errorResponse.setErrorMessage("Not Found");
            errorResponse.setErrorDescription("abcdasd");
            addResponsePojo.setErrorResponse(errorResponse);
            return ResponseEntity.badRequest().body(addResponsePojo);
        }
        else {
            //AddResponsePojo addResponsePojo=new AddResponsePojo();
            addResponsePojo.setTotal(response.getBody().doubleValue());
            System.out.println("Value is" + response.getBody().doubleValue());
            System.out.println("Value is" + addResponsePojo.getTotal());
        }

        if(response.getStatusCode().value()==200){
            addResponsePojo.setStatus("Ok");
           txnHistory.setTxnHistory(requestPojo);
           RedisEntity user=new RedisEntity();
           user.setOperation(requestPojo.getOperation());
           user.setTotal(response.getBody().doubleValue());
            updateCacheRedis(requestPojo.getId(),user);
            ResponseEntity<String> str=restTemplate.postForEntity("http://localhost:8079/kafka/publish?message="+requestPojo.getOperation().toUpperCase()+" Txn is success"+addResponsePojo.getTotal(),null,String.class);
        System.out.print("Kafka Message sent"+str.getBody());
        }else{

            errorResponse.setErrorMessage("Not Found");
            errorResponse.setErrorDescription("abcdasd");
            addResponsePojo.setErrorResponse(errorResponse);
        }
        return ResponseEntity.ok(addResponsePojo);
    }

    public ResponseEntity<Double> performOperation(RequestPojo requestPojo) throws NotFoundException {
        Optional<User> userResult = userRepository.findById(requestPojo.getId());
        String url="";
            if (!userResult.isPresent()) {
                //throw new NotFoundException("User does not exist");
                errorResponse.setErrorMessage("Not Found");
                errorResponse.setErrorDescription("abcdasd");
                //AddResponsePojo addResponsePojo=new AddResponsePojo();
                addResponsePojo.setErrorResponse(errorResponse);
            }

        switch(requestPojo.getOperation()){
            case "add":
            url= addUrl+":"+addPort+addPath+"?value1="+requestPojo.getValue1()+"&value2="+requestPojo.getValue2();
                return  restTemplate.getForEntity(url,Double.class);
            case "subtract":
                url= subtractUrl+":"+subtractPort+subtractPath+"?value1="+requestPojo.getValue1()+"&value2="+requestPojo.getValue2();

            case "divide":
            case "multiply":
            default:
             errorResponse.setErrorMessage("not found");
             errorResponse.setErrorDescription("asdasd");
        }
        return null;
    }

    private boolean updateCacheRedis(Integer userid, RedisEntity userDetails) {

        //Gson json = new Gson();
        //String jsonData = json.toJson(userDetails);
        ObjectMapper Obj = new ObjectMapper();

        try {

            String jsonStr = Obj.writeValueAsString(userDetails);

            // Displaying JSON String
            System.out.println(jsonStr);
            redisTemplate.opsForValue().set("user_" + userid, jsonStr);
        }

        catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }
}
