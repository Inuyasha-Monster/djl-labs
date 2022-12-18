package com.djl.webflux;

import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

public class LocalWebFilter implements WebFilter {

    @Resource
    private DispatcherHandler dispatcherHandler;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().startsWith("/demo")) {
            return dispatcherHandler.handle(exchange);
        }
        return chain.filter(exchange);
    }
}
