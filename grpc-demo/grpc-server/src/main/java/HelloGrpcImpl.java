import grpc.auto.HelloGrpc;
import grpc.auto.HelloRequest;
import grpc.auto.HelloResponse;
import io.grpc.stub.StreamObserver;

/**
 * @author djl
 * @create 2022/5/9 21:25
 */
public class HelloGrpcImpl extends HelloGrpc.HelloImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        responseObserver.onNext(HelloResponse.newBuilder().setMessage("你好呀," + request.getName()).build());
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

            @Override
            public void onCompleted() {
                responseObserver.onNext(HelloResponse.newBuilder().setMessage(stringBuilder.toString()).build());
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 服务端可以多次响应数据
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void sayHelloServerStream(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        for (int i = 0; i < 5; i++) {
            responseObserver.onNext(HelloResponse.newBuilder().setMessage("你好呀," + request.getName()).build());
        }
        responseObserver.onCompleted();
    }

    /**
     * 客户端和服务端可以一直双向推送数据
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
