package com.djl.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(webSocketSession.receive().doOnNext(x -> {
                    System.out.println("接收:" + x.getPayloadAsText());
                }).map(x -> "echo=>" + x.getPayloadAsText())
                .map(webSocketSession::textMessage));
    }
}
