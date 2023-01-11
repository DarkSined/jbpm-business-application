package com.it.sample.components.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Pointcut("@annotation(com.it.sample.components.annotations.LogExecutionTime)")
    public void logExecution() {
        // Pointcut
    }

    @Around("logExecution()")
    public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = null;
        long startTime = System.currentTimeMillis();
        try {
            proceed = pjp.proceed();
        } catch (Exception ex) {
            log.warn("Method: {} encountered exception: {}", pjp.getSignature().getName(), ex);
            throw ex;
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.debug("Method: {} executed in {}ms", pjp.getSignature().getName(), executionTime);
        return proceed;
    }
}
