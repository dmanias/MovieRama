package com.movierama.infrastructure.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.movierama..*.*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Entering method: {}", methodName);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info("Exiting method: {}. Execution time: {} ms", methodName, elapsedTime);
        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.movierama..*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getCause() != null ? ex.getCause() : "NULL");
    }
}