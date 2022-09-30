package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author djl
 */
@SpringBootApplication
public class SimpleHttpServer {
    public static void main(String[] args) {
        SpringApplication.run(SimpleHttpServer.class, args);
    }
}
