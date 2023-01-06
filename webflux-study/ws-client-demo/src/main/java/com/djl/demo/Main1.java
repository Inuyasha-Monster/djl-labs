package com.djl.demo;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.net.URI;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main1 {
    public static void main(String[] args) throws InterruptedException {

        ConnectionProvider provider =
                ConnectionProvider.builder("custom")
                        .maxConnections(1)
                        //.maxIdleTime(Duration.ofSeconds(20))
                        //.maxLifeTime(Duration.ofSeconds(60))
                        //.pendingAcquireTimeout(Duration.ofSeconds(60))
                        //.evictInBackground(Duration.ofSeconds(120))
                        .build();

        final HttpClient httpClient = HttpClient.create(provider);

        WebSocketClient webSocketClient = new ReactorNettyWebSocketClient(httpClient);

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        Random random = new Random();

        //Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = random.nextDouble() + "";

            //final String res = webClient.get().uri("/demo")
            //        .retrieve()
            //        .bodyToMono(String.class)
            //        .block();
            //
            //System.out.println("res = " + res);

            // webSocketClient 如何重用已经建立的ws连接？？

            webSocketClient.execute(
                            URI.create("ws://localhost:8080/event-emitter"),
                            session -> session
                                    .send(Mono.just(session.textMessage(msg)))
                                    // send complete 之后注册一个另外的发射动作
                                    .thenMany(session.receive()
                                            .map(WebSocketMessage::getPayloadAsText)
                                            .doOnNext(x -> System.out.println("接收:" + x)))
                                    .then())
                    .block();

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
