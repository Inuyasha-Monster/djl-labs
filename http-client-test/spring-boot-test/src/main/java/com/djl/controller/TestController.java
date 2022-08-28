package com.djl.controller;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author djl
 */
@RequestMapping("/test")
@RestController
public class TestController {

    private final static CloseableHttpClient CLIENT;

    static {
        CLIENT = HttpClients.custom()
                .setConnectionManagerShared(false)
                .setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build())
                .setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(2000).setConnectionRequestTimeout(2000).build())
                .setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build())
                .setMaxConnPerRoute(10).setMaxConnTotal(100).build();
    }

    private final RestTemplate restTemplate;

    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello1")
    public String hello1() {
        return restTemplate.getForObject("http://localhost:8080/test/hello", String.class) + " haha";
    }

    @GetMapping("/hello2")
    public String hello2() {
        CloseableHttpResponse httpResponse = null;
        String str = "error";
        try {
            final RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(2000)
                    .setConnectTimeout(2000)
                    .setSocketTimeout(3000).build();
            final HttpGet httpGet = new HttpGet("http://localhost:8080/test/hello");
            httpGet.setConfig(config);

            httpResponse = CLIENT.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                EntityUtils.consume(httpResponse.getEntity());
            } else {
                str = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            if (httpResponse != null) {
                EntityUtils.consumeQuietly(httpResponse.getEntity());
            }
        }
        return str;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
