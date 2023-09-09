package com.pks.orderservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class OrderServiceConfig {
    @Bean
    public ServiceInstanceListSupplier supplier(){
        return new ServiceInstanceListSupplier() {
            @Override
            public Flux<List<ServiceInstance>> get() {
                List<ServiceInstance> serviceInstances
                        = new ArrayList<>();
                serviceInstances.add(new DefaultServiceInstance(
                        "PAYMENT-SERVICE",
                        "PAYMENT-SERVICE",
                        "localhost",
                        8080,
                        false
                ));

                serviceInstances.add(new DefaultServiceInstance(
                        "ORDER-SERVICE",
                        "ORDER-SERVICE",
                        "localhost",
                        8080,
                        false
                ));
                serviceInstances.add(new DefaultServiceInstance(
                        "PRODUCT-SERVICE",
                        "PRODUCT-SERVICE",
                        "localhost",
                        8080,
                        false
                ));
                return Flux.just(serviceInstances);
            }

            @Override
            public String getServiceId() {
                return null;
            }
        };
    }
}
