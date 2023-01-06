package com.djl.demo;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        WebSocketClient wsClient = new WebSocketClient(URI.create("ws://localhost:8080/event-emitter")) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {

            }

            @Override
            public void onMessage(String s) {
                System.out.println("接收 = " + s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {

            }

            @Override
            public void onError(Exception e) {

            }
        };
        // 说一下tcp模型：默认情况会重用tcp连接，默认只会建立一个ws连接
        // 说一下线程模型：一个读线程，一个写线程，一个连接checker线程
        wsClient.connectBlocking();
        while (true) {
            wsClient.send("demo");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
