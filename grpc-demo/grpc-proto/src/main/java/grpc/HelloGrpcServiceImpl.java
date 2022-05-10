package grpc;

import grpc.auto.HelloGrpc;
import grpc.auto.HelloRequest;
import grpc.auto.HelloResponse;
import io.grpc.stub.StreamObserver;

/**
 * @author djl
 * @create 2022/5/9 21:25
 */
public class HelloGrpcServiceImpl extends HelloGrpc.HelloImplBase {

    /**
     * 单次请求响应模型
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        final String connId = Common.CONTEXT_KEY_CONN_ID.get();
        System.out.println("connId = " + connId);
        Common.printCurrentThreadInfo("sayHello");
        responseObserver.onNext(HelloResponse.newBuilder().setMessage("hello," + request.getName()).build());
        responseObserver.onCompleted();
    }

    /**
     * 客户端可以推送多次数据
     *
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<HelloRequest> sayHelloClientStream(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<HelloRequest>() {

            private final StringBuilder stringBuilder = new StringBuilder();

            @Override
            public void onNext(HelloRequest helloRequest) {
                stringBuilder.append(helloRequest.getName()).append(";");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            /**
             * 客户端推送数据完成时触发
             */
            @Override
            public void onCompleted() {
                // 执行返回响应数据
                final HelloResponse response = HelloResponse.newBuilder()
                        .setMessage("你们好呀," + stringBuilder).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 服务端可以多次响应数据(其实跟单次请求响应模型一样，只是服务端结束的时机不同)
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void sayHelloServerStream(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        for (int i = 0; i < 5; i++) {
            responseObserver.onNext(HelloResponse.newBuilder().setMessage("你好呀," + i + "," + request.getName()).build());
        }
        responseObserver.onCompleted();
    }

    /**
     * 客户端和服务端可以一直双向推送数据，直到某一方完成
     *
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<HelloRequest> sayHelloDoubleStream(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest helloRequest) {
                final HelloResponse response = HelloResponse.newBuilder()
                        .setMessage("你好呀," + helloRequest.getName())
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
