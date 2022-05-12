package com.djl.config;

import grpc.auto.ConfigGrpc;
import grpc.auto.ConfigServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import transport.request.ConnectionSetupRequest;
import transport.response.AckResponse;
import transport.response.ConfigChangedResponse;
import transport.response.IResponse;
import util.GrpcUtil;

import java.util.concurrent.CountDownLatch;

/**
 * @author djl
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 30000)
                .directExecutor()
                .usePlaintext()
                .build();
        final ConfigServiceGrpc.ConfigServiceStub stub = ConfigServiceGrpc.newStub(managedChannel);

        final StreamObserver<ConfigGrpc.Request> requestStreamObserver = stub.call(new StreamObserver<ConfigGrpc.Response>() {

            /**
             * 接受服务端的返回结果
             * @param response
             */
            @Override
            public void onNext(ConfigGrpc.Response response) {
                final IResponse convertResponse = GrpcUtil.convertResponse(response);
                if (convertResponse instanceof AckResponse) {
                    final AckResponse ackResponse = (AckResponse) convertResponse;
                    System.out.println("client connected to server... response = " + ackResponse);
                    return;
                }
                if (convertResponse instanceof ConfigChangedResponse) {
                    final ConfigChangedResponse changedResponse = (ConfigChangedResponse) convertResponse;
                    System.out.println("client get  server's response = " + convertResponse);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // todo: 重新链接其他服务端
            }

            @Override
            public void onCompleted() {
                // logic not to be there
                latch.countDown();
            }
        });

        final ConnectionSetupRequest setupRequest = new ConnectionSetupRequest();
        setupRequest.setListenConfigKey("name");
        final ConfigGrpc.Request request = GrpcUtil.createRequest(setupRequest);
        requestStreamObserver.onNext(request);
        latch.await();
    }
}
