package com.djlnet.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 * @create 2022/5/8 9:17
 */
@SpringBootApplication
@RestController
public class SimpleConfigServerV1 {
    public static void main(String[] args) {
        SpringApplication.run(SimpleConfigServerV1.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
