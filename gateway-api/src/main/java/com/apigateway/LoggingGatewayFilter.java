package com.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LoggingGatewayFilter extends AbstractGatewayFilterFactory<LoggingGatewayFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingGatewayFilter.class);

    public LoggingGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Получаем ServerHttpRequest
            ServerHttpRequest request = exchange.getRequest();

            // Логируем информацию о запросе
            logger.info("Incoming request: {} {}", request.getMethod().name(), request.getURI());

            // Передаем запрос дальше по цепочке
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Response Status Code: {}", exchange.getResponse().getStatusCode());
            }));
        };
    }

    public static class Config {
        // Здесь могут быть настройки (если нужно)
    }
}