package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * @author djl
 */
public final class JacksonUtil {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String toJson(Object o) {
        return MAPPER.writeValueAsString(o);
    }

    @SneakyThrows
    public static <T> T toObj(String json, Class<T> clazz) {
        return MAPPER.readValue(json, clazz);
    }
}
