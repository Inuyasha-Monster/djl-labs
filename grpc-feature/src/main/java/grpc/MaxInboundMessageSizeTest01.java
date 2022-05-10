package grpc;

import grpc.auto.HelloGrpc;
import grpc.auto.HelloRequest;
import grpc.auto.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author djl
 */
public class MaxInboundMessageSizeTest01 {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = ServerBuilder.forPort(30000)
                // 限制客户端发送数据的大小 defaults to 4 MiB
                .maxInboundMessageSize(10)
                // 限制客户端元数据大小 默认8Kb
                .maxInboundMetadataSize(8 * 1024)
                .addService(new HelloGrpcServiceImpl())
                .build();
        server.start();

        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 30000)
                // 表示接受数据大小 defaults to 4 MiB
                .maxInboundMessageSize(5)
                // 表示接受元数据大小 默认8kb
                .maxInboundMetadataSize(8 * 1024)
                .usePlaintext().build();
        final HelloGrpc.HelloBlockingStub stub = HelloGrpc.newBlockingStub(channel);
        final HelloRequest request = HelloRequest.newBuilder().setName("12345678").build();
        System.out.println("request.toByteArray().length = " + request.toByteArray().length);
        final HelloResponse response = stub.sayHello(request);
        System.out.println("response = " + response);
        server.awaitTermination();
    }
}
