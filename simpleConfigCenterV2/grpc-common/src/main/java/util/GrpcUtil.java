package util;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import grpc.ModelRegistry;
import grpc.auto.ConfigGrpc;
import transport.IRequest;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author djl
 */
public final class GrpcUtil {
    public static IRequest toRequest(ConfigGrpc.Request request) {
        final String type = request.getType();
        final Class<?> modelClass = ModelRegistry.getModelClass(type);
        Objects.requireNonNull(modelClass);
        final String body = request.getBody().getValue().toString(StandardCharsets.UTF_8);
        return (IRequest) JacksonUtil.toObj(body, modelClass);
    }

    public static ConfigGrpc.Response createErrorResponse(String msg) {
        Objects.requireNonNull(msg);
        return ConfigGrpc.Response
                .newBuilder()
                .setType("error")
                .setBody(Any.newBuilder()
                        .setValue(ByteString.copyFrom(msg, StandardCharsets.UTF_8))
                        .build())
                .build();
    }
}
