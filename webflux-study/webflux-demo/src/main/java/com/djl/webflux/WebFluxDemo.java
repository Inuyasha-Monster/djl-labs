package com.djl.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebHandler;

import java.util.Map;

@SpringBootApplication
@EnableWebFlux
public class WebFluxDemo {
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(WebFluxDemo.class, args);

        //final DispatcherHandler bean = context.getBean(DispatcherHandler.class);
        //System.out.println("bean = " + bean);

        final Map<String, WebHandler> beans = context.getBeansOfType(WebHandler.class);
        beans.forEach((x, y) -> System.out.println("name = " + x + " bean ---> " + y));

        final WebHandler webHandler = context.getBean("webHandler", WebHandler.class);

        System.out.println("webHandler = " + webHandler);

        // HttpWebHandlerAdapter [delegate=ExceptionHandlingWebHandler [delegate=FilteringWebHandler [delegate=org.apache.shenyu.web.handler.ShenyuWebHandler@54f2df29]]]
    }

    @Bean("webHandler")
    public MyWebHandler myWebHandler() {
        return new MyWebHandler();
    }

    @Bean("dispatcherHandler")
    public DispatcherHandler dispatcherHandler() {
        return new DispatcherHandler();
    }

    /**
     * 通过 webFilter 实现dispatcherHandler与自定义WebHandler的共存
     *
     * @return
     */
    @Bean
    public WebFilter localWebFilter() {
        return new LocalWebFilter();
    }
}
