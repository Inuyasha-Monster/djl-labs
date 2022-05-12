package grpc.config.center.grpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author djl
 */
@Component
@ConfigurationProperties(prefix = "grpc")
@Data
public class GrpcConfig {
    private Integer port;
}
