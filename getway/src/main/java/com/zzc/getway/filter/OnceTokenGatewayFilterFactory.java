package com.zzc.getway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class OnceTokenGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // 每次响应之前，添加一个一次性令牌
                return chain.filter(exchange).then(Mono.fromRunnable(
                        () -> {
                            ServerHttpResponse response = exchange.getResponse();
                            HttpHeaders headers = response.getHeaders();
                            String value = config.getValue();
                            if("uuid".equalsIgnoreCase(value)){
                                value = UUID.randomUUID().toString();
                            }
                            if("jwt".equalsIgnoreCase(value)){
                                value = "jwt-token";
                            }
                            headers.add(config.getName(),value);
                        }
                ));
            }
        };
    }
}
