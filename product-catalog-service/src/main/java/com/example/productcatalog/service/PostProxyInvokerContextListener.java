package com.example.productcatalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    private final ConfigurableListableBeanFactory factory;

    public PostProxyInvokerContextListener(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names =  context.getBeanDefinitionNames();
        for (String name : names) {
            try {
                BeanDefinition beanDefinition = factory.getBeanDefinition(name);
                String originalClassName = beanDefinition.getBeanClassName();
                if (originalClassName == null || originalClassName.isBlank()) {
                    continue;
                }
                Class<?> originalClass = Class.forName(originalClassName);
                Method[] methods = originalClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        Object bean = context.getBean(name);
                        Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        log.info("Invoking @PostProxy method {} on bean {}", method.getName(), name);
                        currentMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
