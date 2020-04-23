package com.bluesky.shopping.gateway.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class GatewaySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySystemApplication.class, args);
    }

    //定义一个KeyResolver
    @Bean
    public KeyResolver ipKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                String ip = exchange.getRequest().getRemoteAddress().getHostString();
                System.out.println(ip);
                return Mono.just(ip);
            }
        };
    }

}

