package grpc;

import grpc.auto.HelloGrpc;
import grpc.auto.HelloRequest;
import grpc.auto.HelloResponse;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class GrpcExecutorTest {

    private static final ThreadPoolExecutor GRPC_SERVER_EXECUTOR = new ThreadPoolExecutor(4,
            Runtime.getRuntime().availableProcessors(),
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(16384),
            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger(1);

                @Override
                public Thread newThread(Runnable r) {
                    final Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("nacos-grpc-executor-" + counter.getAndIncrement());
                    return thread;
                }
            });

    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = ServerBuilder.forPort(30000).executor(GRPC_SERVER_EXECUTOR)
                .addService(new HelloGrpcServiceImpl())
                .build();
        server.start();
        System.out.println("server stared listen...30000 port");
        GRPC_SERVER_EXECUTOR.execute(() -> {
            final ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("localhost", 30000)
                    .directExecutor()
                    .usePlaintext()
                    .build();

            final boolean channelShutdown = channel.isShutdown();
            System.out.println("channelShutdown = " + channelShutdown);
            final boolean channelTerminated = channel.isTerminated();
            System.out.println("channelTerminated = " + channelTerminated);

            final HelloGrpc.HelloStub stub = HelloGrpc.newStub(channel);
            AtomicInteger nextCount = new AtomicInteger(0);
            AtomicInteger completedCount = new AtomicInteger(0);
            AtomicInteger errorCount = new AtomicInteger(0);
            final StreamObserver<HelloResponse> responseObserver = new StreamObserver<HelloResponse>() {
                @Override
                public void onNext(HelloResponse helloResponse) {
                    nextCount.incrementAndGet();
                    System.out.println("nextCount = " + nextCount.get());
                }

                @Override
                public void onError(Throwable throwable) {
                    errorCount.incrementAndGet();
                    System.out.println("errorCount = " + errorCount.get());
                }

                @Override
                public void onCompleted() {
                    completedCount.incrementAndGet();
                    System.out.println("completedCount = " + completedCount.get());
                }
            };
            for (int i = 0; i < 10; i++) {
                stub.sayHello(HelloRequest.newBuilder().setName("djl").build(), responseObserver);
            }
        });
        server.awaitTermination();
    }
}
