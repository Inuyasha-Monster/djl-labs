package grpc.config.center.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author djl
 */
public class ConfigChangedEvent extends ApplicationEvent {
    private final String configKey;

    public ConfigChangedEvent(Object source, String configKey) {
        super(source);
        this.configKey = configKey;
    }

    public String getConfigKey() {
        return configKey;
    }
}
