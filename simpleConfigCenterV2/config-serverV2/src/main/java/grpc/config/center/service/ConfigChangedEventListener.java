package grpc.config.center.service;

import grpc.GrpcConnection;
import grpc.auto.ConfigGrpc;
import grpc.config.center.event.ConfigChangedEvent;
import grpc.config.center.grpc.GrpcConnectionManger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import transport.response.ConfigChangedResponse;
import util.GrpcUtil;

import java.util.List;

/**
 * @author djl
 */
@Component
public class ConfigChangedEventListener implements ApplicationListener<ConfigChangedEvent> {
    @Override
    public void onApplicationEvent(ConfigChangedEvent configChangedEvent) {
        // 扫描当前服务端持有的client中监听了该key的集合进行推送消息
        final List<GrpcConnection> connections = GrpcConnectionManger.getConnections(configChangedEvent.getConfigKey());
        ConfigChangedResponse response = new ConfigChangedResponse();
        response.setChangedConfigKey(configChangedEvent.getConfigKey());
        final ConfigGrpc.Response grpcResponse = GrpcUtil.createResponse(response);
        for (GrpcConnection connection : connections) {
            connection.responseToClient(grpcResponse);
        }
    }
}
