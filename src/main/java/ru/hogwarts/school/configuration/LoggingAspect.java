package ru.hogwarts.school.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* ru.hogwarts.school.service..*(..))")
    public void logCaller(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();
        String argumentTypes = Arrays.stream(args)
                .map(arg -> arg != null ? arg.getClass().getSimpleName() : "null")
                .collect(Collectors.joining(", "));
        logger.info(" - this calling (parent) method = {}", methodName);
        logger.info(" - calling {}.{}() with input args = {} (from {}.class)", className, methodName, args, argumentTypes);
    }

    @AfterThrowing(pointcut = "execution(* ru.hogwarts.school.service..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();
        logger.error(" - exception in {}.{}(), exception = {}", className, methodName, ex.toString());
    }

}

