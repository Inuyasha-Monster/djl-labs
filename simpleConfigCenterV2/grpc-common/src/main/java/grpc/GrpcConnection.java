package grpc;

import grpc.auto.ConfigGrpc;
import io.grpc.stub.StreamObserver;
import lombok.Data;

/**
 * @author djl
 */
@Data
public final class GrpcConnection {
    private String listenConfigKey;
    private StreamObserver<ConfigGrpc.Response> streamObserver;

    public void responseToClient(ConfigGrpc.Response request) {
        this.getStreamObserver().onNext(request);
    }
}
