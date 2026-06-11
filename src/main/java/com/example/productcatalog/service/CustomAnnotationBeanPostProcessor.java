package com.example.productcatalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;
    private final Map<String, Object> beansMap =  new HashMap<>();
    public CustomAnnotationBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(CustomAnnotation.class)) {
            beansMap.put(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beansMap.containsKey(beanName)) {
            log.info("last part init root service");
        }
        return bean;
    }
}
