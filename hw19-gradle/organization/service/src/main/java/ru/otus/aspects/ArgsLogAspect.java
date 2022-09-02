package ru.otus.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.OverridesAttribute;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Aspect
@Component
public class ArgsLogAspect {

    @Pointcut("@annotation(ArgsLog)")
    public void callLoggedMethods() { }

    @Before("callLoggedMethods()")
    public void log(JoinPoint joinPoint) {
        Optional
                .ofNullable(LoggerFactory.getLogger(joinPoint.getTarget().getClass()))
                .filter(Logger::isInfoEnabled)
                .ifPresent(logger -> {
                    logger.info("call method {} with args:", joinPoint.getSignature().getName());
                    var args = joinPoint.getArgs();
                    IntStream
                            .range(0, args.length)
                            .forEach(index -> logger.info("arg[{}] = {}", index+1, args[index].toString()) );
                });
    }

    @AfterReturning(value = "callLoggedMethods()", returning="result")
    public void logResult(JoinPoint joinPoint, Object result) {
        Optional
                .ofNullable(LoggerFactory.getLogger(joinPoint.getTarget().getClass()))
                .filter(Logger::isInfoEnabled)
                .ifPresent(logger -> logger.info("method {} return: {}", joinPoint.getSignature().getName(), result));
    }
}
