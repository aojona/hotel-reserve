package ru.kirill.hotelreserve.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class LoggingAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Objects
                .requireNonNull(invocation.getThis())
                .getClass()
                .getAnnotation(Logging.class)
                .value()
                .logging(invocation);
        return invocation.proceed();
    }
}