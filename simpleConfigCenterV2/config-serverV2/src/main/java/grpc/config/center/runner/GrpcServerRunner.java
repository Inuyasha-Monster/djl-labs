package grpc.config.center.runner;

import grpc.Common;
import grpc.config.center.grpc.ConfigServiceGrpcImpl;
import grpc.config.center.grpc.GrpcConfig;
import io.grpc.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

import static grpc.Common.CONTEXT_KEY_CONN_ID;
import static grpc.Common.TRANS_KEY_CONN_ID;

/**
 * @author djl
 */
@Component
@Slf4j
public class GrpcServerRunner implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {

    private final GrpcConfig config;

    private Server server;

    public GrpcServerRunner(GrpcConfig config) {
        this.config = config;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        final ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        final ConfigServiceGrpcImpl serviceGrpc = context.getBean(ConfigServiceGrpcImpl.class);

        final ServerInterceptor serverInterceptor = new ServerInterceptor() {
            @Override
            public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
                Context ctx = Context.current()
                        .withValue(CONTEXT_KEY_CONN_ID, serverCall.getAttributes().get(TRANS_KEY_CONN_ID))
                        .withValue(Common.CONTEXT_KEY_CONN_REMOTE_IP, serverCall.getAttributes().get(Common.TRANS_KEY_REMOTE_IP))
                        .withValue(Common.CONTEXT_KEY_CONN_REMOTE_PORT, serverCall.getAttributes().get(Common.TRANS_KEY_REMOTE_PORT))
                        .withValue(Common.CONTEXT_KEY_CONN_LOCAL_PORT, serverCall.getAttributes().get(Common.TRANS_KEY_LOCAL_PORT));
                return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
            }
        };

        final ServerTransportFilter serverTransportFilter = new ServerTransportFilter() {
            @Override
            public Attributes transportReady(Attributes transportAttrs) {
                InetSocketAddress remoteAddress = (InetSocketAddress) transportAttrs
                        .get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
                InetSocketAddress localAddress = (InetSocketAddress) transportAttrs
                        .get(Grpc.TRANSPORT_ATTR_LOCAL_ADDR);
                int remotePort = remoteAddress.getPort();
                int localPort = localAddress.getPort();
                String remoteIp = remoteAddress.getAddress().getHostAddress();
                Attributes attrWrapper = transportAttrs.toBuilder()
                        .set(TRANS_KEY_CONN_ID, System.currentTimeMillis() + "_" + remoteIp + "_" + remotePort)
                        .set(Common.TRANS_KEY_REMOTE_PORT, remotePort)
                        .set(Common.TRANS_KEY_REMOTE_IP, remoteIp)
                        .set(Common.TRANS_KEY_LOCAL_PORT, localPort)
                        .build();
                return attrWrapper;
            }

            @Override
            public void transportTerminated(Attributes transportAttrs) {
                super.transportTerminated(transportAttrs);
            }
        };

        server = ServerBuilder.forPort(config.getPort())
                .addService(serviceGrpc)
                .directExecutor()
                .intercept(serverInterceptor)
                .addTransportFilter(serverTransportFilter)
                .build();
        server.start();
        log.info("grpc server stared listen port: {}", config.getPort());
    }

    @Override
    public void destroy() throws Exception {
        server.shutdown();
        log.info("grpc server shutdown");
    }
}
