package util;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import grpc.PayloadRegistry;
import grpc.auto.ConfigGrpc;
import transport.request.IRequest;
import transport.response.IResponse;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author djl
 */
public final class GrpcUtil {
    public static IRequest convertRequest(ConfigGrpc.Request request) {
        final String type = request.getType();
        final Class<?> modelClass = PayloadRegistry.getPayloadClass(type);
        Objects.requireNonNull(modelClass);
        final String body = request.getBody().getValue().toString(StandardCharsets.UTF_8);
        return (IRequest) JacksonUtil.toObj(body, modelClass);
    }

    public static IResponse convertResponse(ConfigGrpc.Response response) {
        final String type = response.getType();
        final Class<?> modelClass = PayloadRegistry.getPayloadClass(type);
        Objects.requireNonNull(modelClass);
        final String body = response.getBody().getValue().toString(StandardCharsets.UTF_8);
        return (IResponse) JacksonUtil.toObj(body, modelClass);
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

    public static ConfigGrpc.Response createResponse(IResponse response) {
        final String json = JacksonUtil.toJson(response);
        final Any body = Any.newBuilder().setValue(ByteString.copyFrom(json, StandardCharsets.UTF_8)).build();
        return ConfigGrpc.Response
                .newBuilder()
                .setType(response.getClass().getSimpleName())
                .setBody(body)
                .build();
    }

    public static ConfigGrpc.Request createRequest(IRequest request) {
        final String json = JacksonUtil.toJson(request);
        final Any body = Any.newBuilder().setValue(ByteString.copyFrom(json, StandardCharsets.UTF_8)).build();
        return ConfigGrpc.Request
                .newBuilder()
                .setType(request.getClass().getSimpleName())
                .setBody(body)
                .build();
    }
}
