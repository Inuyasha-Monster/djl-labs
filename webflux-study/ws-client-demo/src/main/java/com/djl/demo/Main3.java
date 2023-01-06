package com.djl.demo;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.net.URI;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main3 {
    public static void main(String[] args) throws InterruptedException {
        final ConnectionProvider provider = ConnectionProvider.builder("test")
                .maxConnections(1)
                .build();
        final HttpClient httpClient = HttpClient.create(provider);
        WebSocketClient webSocketClient = new ReactorNettyWebSocketClient(httpClient);

        // webSocketClient 如何重用已经建立的ws连接？？
        final WebSocketSession[] webSocketSession = new WebSocketSession[1];

        webSocketClient.execute(
                        URI.create("ws://localhost:8080/event-emitter"),
                        session -> {
                            webSocketSession[0] = session;
                            return Mono.empty();
                        })
                .subscribe();

        TimeUnit.SECONDS.sleep(3);

        Random random = new Random();

        //Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = random.nextDouble() + "";

            final WebSocketSession session = webSocketSession[0];
            session.send(Mono.just(session.textMessage(msg)))
                    .thenMany(session.receive()
                            .map(WebSocketMessage::getPayloadAsText)
                            .doOnNext(x -> System.out.println("接收:" + x)))
                    .then()
                    .subscribe();

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
