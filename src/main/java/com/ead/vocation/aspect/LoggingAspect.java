package com.ead.vocation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String YELLOW = "\u001B[33m";
     private static final String RESET = "\u001B[0m";

    @Around("execution(* com.ead.vocation.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        
        Object result = joinPoint.proceed();  

        long elapsedTime = System.currentTimeMillis() - start;
       logger.info(YELLOW + " PERFORMANCE: Method {} executed in {} ms" + RESET, joinPoint.getSignature(), elapsedTime);

        return result;
    }
}
