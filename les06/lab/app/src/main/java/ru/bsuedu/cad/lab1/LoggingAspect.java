package ru.bsuedu.cad.lab;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LoggingAspect {
    
    @Around("execution(* ru.bsuedu.cad.lab.CSVParser.parse(..))")
    public Object measureParseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        
        Object result = joinPoint.proceed();
        
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000; // в миллисекундах
        
        System.out.println("⏱️ [АОП] Метод " + joinPoint.getSignature().getName() + " выполнен за " + durationMs + " мс");
        
        return result;
    }
}