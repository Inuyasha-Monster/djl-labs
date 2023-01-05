package com.djl.demo;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main1 {
    public static void main(String[] args) throws InterruptedException {

        final HttpClient httpClient = HttpClient.create();

        WebSocketClient webSocketClient = new ReactorNettyWebSocketClient(httpClient);

        Random random = new Random();

        //Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = random.nextDouble() + "";
            webSocketClient.execute(
                            URI.create("ws://localhost:9195/event-emitter"),
                            session -> session
                                    .send(Mono.just(session.textMessage(msg)))
                                    .thenMany(session.receive()
                                            .map(WebSocketMessage::getPayloadAsText)
                                            .doOnNext(x -> System.out.println("接收：" + x)))
                                    .then())
                    .subscribe();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
