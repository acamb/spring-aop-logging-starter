package ac.aop.logging.starter.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LoggingConfiguration {

    @Bean
    @Autowired
    public Advisor controllerLogger(LoggingInterceptor interceptor,LoggingProperties loggingProperties){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(loggingProperties.getControllers());
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }

    @Bean
    @Autowired
    public Advisor serviceLogger(LoggingInterceptor interceptor,LoggingProperties loggingProperties){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(loggingProperties.getServices());
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }

    /**
     * ObjectMapper to log object as json, filtering @DontLog fields/parameters
     * @return an ObjectMapper instance dedicated for logging
     * @see #mapper()
     * TODO: print @DontLog fields as name: {SKIPPED} like in {@see LoggingInterceptor#extractParameters()}
     */
    @Bean
    @Qualifier("loggerMapper")
    public ObjectMapper loggerMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(AnnotatedMember m) {
                return _findAnnotation(m, DontLog.class) != null || _findAnnotation(m, JsonIgnore.class) != null;
            }
        });
        return objectMapper;
    }


    /**
     * @param
     * @return 'standard' object mapper for serialization
     * @see #loggerMapper()
     */
    @Bean
    @Primary
    public ObjectMapper mapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

}
