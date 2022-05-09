import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author djl
 * @create 2022/5/9 21:17
 */
public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = ServerBuilder.forPort(30000)
                .addService(new HelloGrpcImpl())
                .build();
        server.start();
        System.out.println("grpc server started...wait for shutdown..");
        server.awaitTermination();
    }
}
