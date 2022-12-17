package com.djl.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@SpringBootApplication
public class WebFluxDemo {
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(WebFluxDemo.class, args);

        final DispatcherHandler bean = context.getBean(DispatcherHandler.class);
        System.out.println("bean = " + bean);

        final Map<String, WebHandler> beans = context.getBeansOfType(WebHandler.class);
        beans.forEach((x, y) -> System.out.println("bean ---> " + y));
    }

    @Bean("myWebHandler")
    public WebHandler myWebHandler() {
        return exchange -> {
            final DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("i am myWebHandler".getBytes(StandardCharsets.UTF_8));
            return exchange.getResponse().writeWith(Mono.just(dataBuffer).doOnNext(data -> exchange.getResponse().getHeaders().setContentLength(data.readableByteCount())));
        };
    }
}
