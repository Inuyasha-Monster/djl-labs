package com.djl.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/test")
    public Mono<String> test() {
        // Thread.currentThread().getName() = reactor-http-nio-2
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        return Mono.just("just for test");
    }
}
