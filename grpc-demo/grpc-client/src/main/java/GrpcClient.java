import com.google.protobuf.TextFormat;
import grpc.auto.HelloGrpc;
import grpc.auto.HelloRequest;
import grpc.auto.HelloResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author djl
 * @create 2022/5/9 21:40
 */
public class GrpcClient {
    public static void main(String[] args) {
        Channel channel = ManagedChannelBuilder.forAddress("localhost", 30000).usePlaintext().build();
        final HelloGrpc.HelloBlockingStub stub = HelloGrpc.newBlockingStub(channel);
        final HelloResponse response = stub.sayHello(HelloRequest.newBuilder().setName("djl").build());
        System.out.println("response = " + TextFormat.printToUnicodeString(response));
    }
}
