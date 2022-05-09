import com.google.protobuf.InvalidProtocolBufferException;
import grpc.auto.HelloRequest;

import java.util.Arrays;

/**
 * @author djl
 * @create 2022/5/9 21:10
 */
public class GrpcTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        final HelloRequest request = HelloRequest.newBuilder().setName("djl").build();
        System.out.println("request = " + request);
        final byte[] bytes = request.toByteArray();
        // bytes = [10, 3, 100, 106, 108]
        System.out.println("bytes = " + Arrays.toString(bytes));
        // bytes[0] = 1;
        final HelloRequest parse = HelloRequest.parseFrom(bytes);
        System.out.println("parse = " + parse);
    }
}
