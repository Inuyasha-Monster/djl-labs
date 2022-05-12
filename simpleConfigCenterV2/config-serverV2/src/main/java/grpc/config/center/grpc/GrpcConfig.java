package grpc.config.center.grpc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author djl
 */
@Component
@ConfigurationProperties(prefix = "grpc")
public class GrpcConfig {
    private Integer port;

    public Integer getPort() {
        return port;
    }
}
