package grpc.config.center.grpc;

import grpc.GrpcConnection;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author djl
 */
public final class GrpcConnectionManger {

    private static final Map<String, GrpcConnection> CONNECTION_MAP = new ConcurrentHashMap<>();

    public static void register(String connId, GrpcConnection connection) {
        CONNECTION_MAP.putIfAbsent(connId, connection);
    }

    public static GrpcConnection getConn(String connId) {
        return CONNECTION_MAP.get(connId);
    }

    public static void disconnect(String connId) {
        CONNECTION_MAP.remove(connId);
    }

    public static List<GrpcConnection> getConnections(String configKey) {
        return CONNECTION_MAP.values().stream()
                .filter(x -> x.getListenConfigKey().equals(configKey))
                .collect(Collectors.toList());
    }

}
