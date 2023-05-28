package ru.kirill.hotelreserve.config.logging;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class LoggingBeanPostProcessor implements BeanPostProcessor {

    private final LoggingAdvice advice;

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Logging.class)) {
            ProxyFactory factory = new ProxyFactory(bean);
            log.info("Created logging proxy of {}", beanName);
            factory.addAdvice(advice);
            return factory.getProxy();
        }
        return bean;
    }
}
