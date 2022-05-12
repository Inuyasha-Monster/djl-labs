package grpc;

import grpc.auto.HelloGrpc;
import grpc.auto.HelloRequest;
import grpc.auto.HelloResponse;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static grpc.Common.*;

/**
 * @author djl
 */
public class InterceptorTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 服务端拦截器
        final ServerInterceptor serverInterceptor = new ServerInterceptor() {
            @Override
            public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
                Context ctx = Context.current()
                        .withValue(CONTEXT_KEY_CONN_ID, serverCall.getAttributes().get(TRANS_KEY_CONN_ID))
                        .withValue(Common.CONTEXT_KEY_CONN_REMOTE_IP, serverCall.getAttributes().get(Common.TRANS_KEY_REMOTE_IP))
                        .withValue(Common.CONTEXT_KEY_CONN_REMOTE_PORT, serverCall.getAttributes().get(Common.TRANS_KEY_REMOTE_PORT))
                        .withValue(Common.CONTEXT_KEY_CONN_LOCAL_PORT, serverCall.getAttributes().get(Common.TRANS_KEY_LOCAL_PORT));
                Common.printCurrentThreadInfo("interceptCall");
                return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
            }
        };
        // 服务端传输过滤器
        final ServerTransportFilter serverTransportFilter = new ServerTransportFilter() {
            @Override
            public Attributes transportReady(Attributes transportAttrs) {
                InetSocketAddress remoteAddress = (InetSocketAddress) transportAttrs
                        .get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
                InetSocketAddress localAddress = (InetSocketAddress) transportAttrs
                        .get(Grpc.TRANSPORT_ATTR_LOCAL_ADDR);
                int remotePort = remoteAddress.getPort();
                int localPort = localAddress.getPort();
                String remoteIp = remoteAddress.getAddress().getHostAddress();
                Attributes attrWrapper = transportAttrs.toBuilder()
                        .set(TRANS_KEY_CONN_ID, System.currentTimeMillis() + "_" + remoteIp + "_" + remotePort)
                        .set(Common.TRANS_KEY_REMOTE_PORT, remotePort)
                        .set(Common.TRANS_KEY_REMOTE_IP, remoteIp)
                        .set(Common.TRANS_KEY_LOCAL_PORT, localPort)
                        .build();
                String connectionId = attrWrapper.get(TRANS_KEY_CONN_ID);
                System.out.println("Connection transportReady,connectionId = " + connectionId);
                Common.printCurrentThreadInfo("Connection transportReady");
                return attrWrapper;
            }

            @Override
            public void transportTerminated(Attributes transportAttrs) {
                super.transportTerminated(transportAttrs);
            }
        };
        final Server server = ServerBuilder.forPort(30000)
                .intercept(serverInterceptor)
                .addTransportFilter(serverTransportFilter)
                .addService(new HelloGrpcServiceImpl())
                .build();
        server.start();
        EXECUTOR.execute(() -> {
            final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 30000)
                    .directExecutor()
                    .usePlaintext().build();
            final HelloGrpc.HelloStub stub = HelloGrpc.newStub(channel);
            final StreamObserver<HelloResponse> responseObserver = new StreamObserver<HelloResponse>() {
                @Override
                public void onNext(HelloResponse helloResponse) {
                    System.out.println("helloResponse = " + helloResponse);
                    final String connId = CONTEXT_KEY_CONN_ID.get();
                    // connId = null
                    System.out.println("connId = " + connId);
                    printCurrentThreadInfo("onNext");
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onCompleted() {

                }
            };
            //final StreamObserver<HelloRequest> requestStreamObserver = stub.sayHelloClientStream(responseObserver);
            //requestStreamObserver.onNext(HelloRequest.newBuilder().setName("djl").build());
            //requestStreamObserver.onCompleted();

            final StreamObserver<HelloRequest> streamObserver = stub.sayHelloDoubleStream(responseObserver);

            for (int i = 0; i < 2; i++) {
                //stub.sayHello(HelloRequest.newBuilder().setName("djl").build(), responseObserver);
                streamObserver.onNext(HelloRequest.newBuilder().setName("djl").build());
            }
            streamObserver.onCompleted();
        });
        server.awaitTermination();
    }

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
}
