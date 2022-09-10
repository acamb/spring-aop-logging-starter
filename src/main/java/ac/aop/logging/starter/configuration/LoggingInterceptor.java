package ac.aop.logging.starter.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;

@Component
public class LoggingInterceptor implements MethodInterceptor {

    Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Autowired
    @Qualifier("loggerMapper")
    ObjectMapper jsonMapper;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Annotation[][] parameterAnnotations = invocation.getMethod().getParameterAnnotations();
        Object[] args = invocation.getArguments();

        String arguments = extractArguments(args,parameterAnnotations);
        logger.info("[Invocation] Class:{},Method:{},Parameters:{}",
                invocation.getMethod().getDeclaringClass().getName(),invocation.getMethod().getName(),arguments);
        Object result = invocation.proceed();
        Object[] resultArray = {result};
        String resultArguments = extractArguments(resultArray,null);
        logger.info("[Invocation End] Class:{},Method:{},Result:{}",
                invocation.getMethod().getDeclaringClass().getName(),invocation.getMethod().getName(),resultArguments);
        return result;
    }

    String extractArguments(Object[] arguments,Annotation[][] parameterAnnotations){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<arguments.length;i++){
            Object arg = arguments[i];
            if(parameterAnnotations != null && Arrays.stream(parameterAnnotations[i]).map(Annotation::annotationType).anyMatch(e -> e == DontLog.class)){
                sb.append("[ index: ").append(i).append(", value{SKIPPED}]");
            }
            else{
                try {
                    sb.append("[index: ").append(i).append(",value: ").append(jsonMapper.writeValueAsString(arg)).append("]");
                } catch (JsonProcessingException e) {
                    sb.append("[index: ").append(i).append(", value{Not serializable}]");
                    logger.warn("Exception serializing argument",e);
                }
            }
        }
        return sb.toString();
    }
}
