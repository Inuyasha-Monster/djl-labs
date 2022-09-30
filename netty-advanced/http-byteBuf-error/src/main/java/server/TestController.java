package server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author djl
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) throws IOException {
        final byte[] bytes = new byte[request.getContentLength()];
        request.getInputStream().read(bytes, 0, bytes.length);
        final String msg = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(LocalDateTime.now() + " .... test ");
        return "ok ---> " + msg;
    }
}
