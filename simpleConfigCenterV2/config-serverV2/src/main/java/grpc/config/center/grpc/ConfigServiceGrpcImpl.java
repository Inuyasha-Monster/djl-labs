package grpc.config.center.grpc;

import grpc.auto.ConfigGrpc;
import grpc.auto.ConfigServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import transport.ConnectionSetupRequest;
import transport.IRequest;
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
        final StreamObserver<ConfigGrpc.Request> streamObserver = new StreamObserver<ConfigGrpc.Request>() {

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
                final IRequest req = GrpcUtil.toRequest(request);
                if (req instanceof ConnectionSetupRequest) {
                    final ConnectionSetupRequest connectionSetupRequest = (ConnectionSetupRequest) req;

                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("grpc server onError", throwable);
                responseObserver.onCompleted();
            }

            /**
             * 客户端完成通讯触发
             */
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
        return streamObserver;
    }
}
