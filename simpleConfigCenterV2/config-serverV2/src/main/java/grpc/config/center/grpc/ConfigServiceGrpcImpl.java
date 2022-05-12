package grpc.config.center.grpc;

import grpc.Common;
import grpc.GrpcConnection;
import grpc.auto.ConfigGrpc;
import grpc.auto.ConfigServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import transport.request.ConnectionSetupRequest;
import transport.request.IRequest;
import transport.response.AckResponse;
import util.GrpcUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author djl
 */
@Service
@Slf4j
public class ConfigServiceGrpcImpl extends ConfigServiceGrpc.ConfigServiceImplBase {

    @Override
    public StreamObserver<ConfigGrpc.Request> call(StreamObserver<ConfigGrpc.Response> responseObserver) {
        return new StreamObserver<ConfigGrpc.Request>() {

            private final String connId = Common.CONTEXT_KEY_CONN_ID.get();

            /**
             * 客户端请求抵达
             * @param request
             */
            @Override
            public void onNext(ConfigGrpc.Request request) {
                if (request == null || StringUtils.isEmpty(request.getType()) || StringUtils.isEmpty(request.getBody().getValue().toString(StandardCharsets.UTF_8))) {
                    final ConfigGrpc.Response response =
                            GrpcUtil.createErrorResponse("request param is error");
                    responseObserver.onNext(response);
                    return;
                }
                // 判断是否为客户建立链接的请求
                final IRequest req = GrpcUtil.convertRequest(request);
                if (req instanceof ConnectionSetupRequest) {
                    final ConnectionSetupRequest connectionSetupRequest = (ConnectionSetupRequest) req;
                    final GrpcConnection connection = new GrpcConnection();
                    connection.setListenConfigKey(connectionSetupRequest.getListenConfigKey());
                    connection.setStreamObserver(responseObserver);
                    GrpcConnectionManger.register(connId, connection);

                    final ConfigGrpc.Response response = GrpcUtil.createResponse(new AckResponse());
                    connection.responseToClient(response);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("grpc server onError", throwable);
                responseObserver.onCompleted();
                GrpcConnectionManger.disconnect(connId);
            }

            /**
             * 客户端完成通讯触发
             */
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                GrpcConnectionManger.disconnect(connId);
            }
        };
    }
}
