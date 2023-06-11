package ru.kirill.hotelreserve.enums;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Invocation;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Objects;

@Slf4j
public enum LayerType {

    CONTROLLER {
        @Override
        public void logging(MethodInvocation invocation) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getRequest();
            log.info(
                    "Class: {}, request: {} {}, args: {}",
                    getClassName(invocation),
                    request.getMethod(),
                    request.getRequestURI(),
                    invocation.getArguments()
            );
        }
    },

    DEFAULT_LAYER {
        @Override
        public void logging(MethodInvocation invocation) {
            log.info(
                    "Class: {}, method: {}, args: {}",
                    getClassName(invocation),
                    invocation.getMethod().getName(),
                    invocation.getArguments()
            );
        }
    };

    public abstract void logging(MethodInvocation invocation);

    public String getClassName(Invocation invocation) {
        return Objects
                .requireNonNull(invocation.getThis())
                .getClass()
                .getSimpleName();
    }
}
