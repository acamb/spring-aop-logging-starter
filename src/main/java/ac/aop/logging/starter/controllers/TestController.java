package ac.aop.logging.starter.controllers;

import ac.aop.logging.starter.configuration.DontLog;
import ac.aop.logging.starter.model.HelloResponse;
import ac.aop.logging.starter.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    HelloService helloService;

    @GetMapping("/hello")
    public ResponseEntity<HelloResponse> hello(@RequestParam @DontLog String a, String b, @DontLog String c){
        return new ResponseEntity<>(new HelloResponse(a,b,c),HttpStatus.OK);
    }

    @GetMapping("/hello2")
    public ResponseEntity<HelloResponse> hello2(@RequestParam @DontLog String a, String b, @DontLog String c){
        HelloResponse body = helloService.hello(a,b,c);
        return new ResponseEntity<>(body,HttpStatus.OK);
    }

    @Autowired
    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
}
