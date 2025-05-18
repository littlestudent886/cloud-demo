package com.zzc.order.config;

import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import feign.Logger;

@Configuration
public class OrderServiceConfig {

    @LoadBalanced //注解式负载均衡
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

//    @Bean
    Retryer retryer(){
        return new Retryer.Default();
    }
}
