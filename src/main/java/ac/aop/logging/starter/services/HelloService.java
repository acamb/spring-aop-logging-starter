package ac.aop.logging.starter.services;

import ac.aop.logging.starter.model.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public HelloResponse hello(String a, String b, String c){
        return new HelloResponse(a,b,c);
    }
}
