package grpc.config.center.grpc;

import grpc.GrpcConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author djl
 */
public final class GrpcConnectionManger {

    private static final Map<String, GrpcConnection> CONNECTION_MAP = new ConcurrentHashMap<>();

    public static void connect(String connId, GrpcConnection connection) {
        CONNECTION_MAP.putIfAbsent(connId, connection);
    }

    public static void disconnect(String connId) {
        CONNECTION_MAP.remove(connId);
    }
}
