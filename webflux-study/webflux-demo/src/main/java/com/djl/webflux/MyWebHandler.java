package com.djl.webflux;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class MyWebHandler implements WebHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange) {
        final DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("i am myWebHandler".getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(dataBuffer).doOnNext(data -> exchange.getResponse().getHeaders().setContentLength(data.readableByteCount())));
    }
}
