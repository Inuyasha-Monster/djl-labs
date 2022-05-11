package grpc.config.center.grpc;

import grpc.auto.ConfigGrpc;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author djl
 */
public final class GrpcConnectionManger {
    private static final Map<String, StreamObserver<ConfigGrpc.Response>> CLIENTS = new ConcurrentHashMap<>();
    
}
