package grpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author djl
 */
public class ModelRegistry {

    private static final Map<String, Class<?>> CLASS_MAP = new ConcurrentHashMap<>();

    static {

    }

    public static Class<?> getModelClass(String type) {
        return CLASS_MAP.get(type);
    }
}
