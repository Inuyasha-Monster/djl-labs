import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import grpc.auto.HelloGrpc;
import grpc.auto.HelloRequest;
import grpc.auto.HelloResponse;
import grpc.auto.Payload;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * @author djl
 * @create 2022/5/9 21:40
 */
public class GrpcClientTest {

    private static Channel channel;

    @BeforeAll
    public static void before() {
        channel = ManagedChannelBuilder.forAddress("localhost", 30000)
                .usePlaintext()
                .build();
    }

    @Test
    public void testAny() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        final HelloGrpc.HelloStub stub = HelloGrpc.newStub(channel);
        final Payload request = Payload.newBuilder()
                .setType("hello")
                .setBody(Any.newBuilder()
                        .setValue(ByteString.copyFrom("djl", StandardCharsets.UTF_8))
                        .build())
                .build();
        stub.call(request, new StreamObserver<Payload>() {
            @Override
            public void onNext(Payload payload) {
                final String type = payload.getType();
                final String body = payload.getBody().getValue().toString(StandardCharsets.UTF_8);
                System.out.println("type = " + type + " body = " + body);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void test() {
        final HelloGrpc.HelloBlockingStub stub = HelloGrpc.newBlockingStub(channel);

        final ManagedChannel managedChannel = (ManagedChannel) stub.getChannel();
        final boolean managedChannelTerminated = managedChannel.isTerminated();
        System.out.println("managedChannelTerminated = " + managedChannelTerminated);
        final boolean managedChannelShutdown = managedChannel.isShutdown();
        System.out.println("managedChannelShutdown = " + managedChannelShutdown);

        // 单次请求响应测试
        final HelloResponse response = stub.sayHello(HelloRequest.newBuilder().setName("djl").build());
        System.out.println("response = " + TextFormat.printToUnicodeString(response));
    }

    @Test
    public void testClientStreaming() throws InterruptedException {
        final HelloGrpc.HelloStub stub = HelloGrpc.newStub(channel);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        final StreamObserver<HelloRequest> requestStreamObserver = stub.sayHelloClientStream(new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse helloResponse) {
                System.out.println("接受到服务端消息:" + TextFormat.printToUnicodeString(helloResponse));
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        for (int i = 0; i < 5; i++) {
            final HelloRequest item = HelloRequest.newBuilder().setName("name" + i).build();
            requestStreamObserver.onNext(item);
        }
        requestStreamObserver.onCompleted();

        System.out.println("client send message again");
        // java.lang.IllegalStateException: Stream is already completed, no further calls are allowed
        requestStreamObserver.onNext(HelloRequest.newBuilder().setName("fk").build());

        countDownLatch.await();
    }

    @Test
    public void testServerStreaming() throws InterruptedException {
        final HelloGrpc.HelloStub stub = HelloGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        final HelloRequest request = HelloRequest.newBuilder().setName("djl").build();
        final StreamObserver<HelloResponse> responseStreamObserver = new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse helloResponse) {
                System.out.println("接受到服务端消息:" + TextFormat.printToUnicodeString(helloResponse));
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
        stub.sayHelloServerStream(request, responseStreamObserver);
        latch.await();
    }

    @Test
    public void testBiStream() throws InterruptedException {
        final HelloGrpc.HelloStub stub = HelloGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        final StreamObserver<HelloResponse> responseObserver = new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse helloResponse) {
                System.out.println("接受到服务端消息:" + TextFormat.printToUnicodeString(helloResponse));
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
        final StreamObserver<HelloRequest> requestStreamObserver = stub.sayHelloDoubleStream(responseObserver);
        for (int i = 0; i < 5; i++) {
            final HelloRequest item = HelloRequest.newBuilder().setName("name" + i).build();
            requestStreamObserver.onNext(item);
        }
        requestStreamObserver.onCompleted();
        latch.await();
    }
}
