package wooklee.koreaplaner.configs.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import wooklee.koreaplaner.controllers.responses.DefaultResponse;

import java.sql.Timestamp;

@Component
@Aspect
public class CustomLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(CustomLogAspect.class);


    @Around("execution(* wooklee.koreaplaner.controllers.*Controller.*(..))")
    public Object controllerLog(ProceedingJoinPoint jp) throws Throwable {

        long startTime = System.currentTimeMillis();
        ResponseEntity<DefaultResponse> re = (ResponseEntity<DefaultResponse>) jp.proceed();

        long endTime = System.currentTimeMillis();
        Signature s = jp.getSignature();

        Class<?> c = s.getDeclaringType();


        StringBuilder sb = new StringBuilder();
        sb.append("[Current Time] ").append(new Timestamp(System.currentTimeMillis())).append("\n");
        sb.append("[Controller Name] ").append(c.getName()).append("\n");
        sb.append("[API Name] ").append(s.getName()).append("\n");
        sb.append("[Request Objects] ").append("\n");
        Object obj[] = jp.getArgs();
        for(int a=0;a<obj.length;a++){
            if(obj[a]==null){
                sb.append("\t\t"+obj[a]).append("\n");
            }else{
                sb.append("\t\t"+obj[a].toString()).append("\n");
            }

        }
        sb.append("[Response Objects] ").append(re.getStatusCode()).append("\n");
        sb.append(re.getBody().toString()).append("\n");
        sb.append("[Total Run Time] ").append(endTime - startTime).append("ms");
        logger.info(sb.toString());

        return re;
    }

}
