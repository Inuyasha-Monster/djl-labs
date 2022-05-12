package grpc.config.center;

import grpc.config.center.service.ConfigPushService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 */
@SpringBootApplication
@RestController
public class ConfigCenterApp {

    private final ConfigPushService pushService;

    public ConfigCenterApp(ConfigPushService pushService) {
        this.pushService = pushService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterApp.class, args);
    }

    @GetMapping("/")
    public String test() {
        return "ok";
    }

    @GetMapping("/config/changed/{key}")
    public String configChanged(@PathVariable("key") String key) {
        pushService.publishConfigEvent(key);
        return "push ok";
    }
}
