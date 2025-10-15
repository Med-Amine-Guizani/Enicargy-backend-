package org.example.backendenicargy.Aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  // Pointcut pour toutes les méthodes dans le package des controllers
  @Pointcut("execution(* org.example.backendenicargy.Services.*.*(..)) || " +
          "execution(* org.example.backendenicargy.Controllers.*.*(..))")
    public void Methods() {}

    @Before("Methods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        logger.info("➡️ [BEFORE] Appel de la méthode du controller : {} avec les arguments : {}", methodName, args);
    }

    @After("Methods()")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("✅ [AFTER] Fin d'exécution de la méthode du controller : {}", methodName);
    }
}

