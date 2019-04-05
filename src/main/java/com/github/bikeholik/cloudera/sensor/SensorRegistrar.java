package com.github.bikeholik.cloudera.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.UUID;

@RequiredArgsConstructor
class SensorRegistrar implements BeanDefinitionRegistryPostProcessor {

    private final int sensorsCount;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // NOP
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (int i = 0; i < sensorsCount; i++) {
            registry.registerBeanDefinition("sensor-" + i, getSensorDefinition());
        }
    }

    private BeanDefinition getSensorDefinition() {
        return BeanDefinitionBuilder.rootBeanDefinition(SensorDataGenerator.class)
                .addConstructorArgValue(UUID.randomUUID())
                .addConstructorArgReference("kafkaTemplate")
                .getBeanDefinition();
    }


}
