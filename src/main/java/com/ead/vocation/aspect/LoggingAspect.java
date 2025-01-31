package com.ead.vocation.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";

    @Around("execution(* com.ead.vocation.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        logger.info(YELLOW + "[PERFORMANCE] Method {} executed in {} ms" + RESET, joinPoint.getSignature(),
                elapsedTime);

        return result;
    }

    @Before("execution(* com.ead.vocation.controller..*(..)) || execution(* com.ead.vocation.service..*(..)) || execution(* com.ead.vocation.repository..*(..))")
    public void logMethodCallInAllLayers(JoinPoint joinPoint) {
        String methodSignature = joinPoint.getSignature().toShortString();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String color = getColorForClass(className);
        String prefix = getPrefixForClass(className);

        String args = className.contains(".controller.") ? ""
                : " with arguments " + Arrays.toString(joinPoint.getArgs());

        logger.info("{}[{}] METHOD CALL: {}{}{}", color, prefix, methodSignature, args, RESET);
    }

    private String getPrefixForClass(String className) {
        if (className.contains(".controller.")) {
            return "CONTROLLER";
        } else if (className.contains(".service.")) {
            return "SERVICE";
        } else if (className.contains(".repository.")) {
            return "REPOSITORY";
        } else if (className.contains(".shared.")) {
            return "SHARED";
        }
        return "UNKNOWN";
    }

    private String getColorForClass(String className) {
        if (className.contains(".controller.")) {
            return RED;
        } else if (className.contains(".service.")) {
            return GREEN;
        } else if (className.contains(".repository.")) {
            return BLUE;
        } else if (className.contains(".shared.")) {
            return PURPLE;
        }

        return RESET;
    }
}
