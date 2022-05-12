package grpc;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import transport.IPayload;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author djl
 */
public class PayloadRegistry {

    private static final Map<String, Class<?>> CLASS_MAP = new ConcurrentHashMap<>();

    private static Set<Class<?>> findAllClassesUsingReflectionsLibrary(String packageName, Class<?> parentClass) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(true));
        return new HashSet<>(reflections.getSubTypesOf(parentClass));
    }

    static {
        final Set<Class<?>> transport =
                findAllClassesUsingReflectionsLibrary("transport", IPayload.class);
        transport.forEach(x -> CLASS_MAP.put(x.getSimpleName(), x));
    }

    public static Class<?> getPayloadClass(String type) {
        return CLASS_MAP.get(type);
    }
}
