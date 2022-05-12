package grpc.config.center.service;

import grpc.config.center.event.ConfigChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author djl
 */
@Component
public class ConfigPushService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ConfigPushService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishConfigEvent(final String configKey) {
        ConfigChangedEvent event = new ConfigChangedEvent(this, configKey);
        this.applicationEventPublisher.publishEvent(event);
    }
}
