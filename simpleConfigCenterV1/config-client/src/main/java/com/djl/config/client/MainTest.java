package com.djl.config.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author djl
 */
@Slf4j
public class MainTest {

    private final static ScheduledExecutorService CONFIG_LONG_POLL_EXECUTOR = Executors.newScheduledThreadPool(2);

    private final static ExecutorService CONFIG_QUERY_EXECUTOR = Executors.newSingleThreadExecutor();

    /**
     * 客户端长轮训监听配置任务
     */
    private static class ConfigLongPollTask implements Runnable {

        private final String listenConfigKey;

        private ConfigLongPollTask(String listenConfigKey) {
            this.listenConfigKey = listenConfigKey;
        }

        @Override
        public void run() {
            try {
                final URL url = new URL("http://127.0.0.1:20000/config/listen/" + this.listenConfigKey);
                HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(30000);
                final String message = connection.getResponseMessage();
                if (message != null && message.trim().length() > 0 && message.contains("changed")) {
                    System.out.println("long poll response message = " + message);
                    CONFIG_QUERY_EXECUTOR.execute(new ConfigQueryTask(this.listenConfigKey));
                } else {
                    System.out.println(this.listenConfigKey + " 配置值无变化");
                }
            } catch (IOException e) {
                log.error("long poll task error", e);
            }
        }
    }

    /**
     * 查询配置任务
     */
    private static class ConfigQueryTask implements Runnable {

        private final String changedConfigKey;

        public ConfigQueryTask(String changedConfigKey) {
            this.changedConfigKey = changedConfigKey;
        }

        @Override
        public void run() {
            final URL url;
            try {
                url = new URL("http://127.0.0.1:20000/config/get/" + this.changedConfigKey);
                HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(3000);
                final String responseMessage = connection.getResponseMessage();
                System.out.println(this.changedConfigKey + " new config value = " + responseMessage);
                listenConfigValue = responseMessage;
            } catch (IOException e) {
                log.error("ConfigQueryTask error", e);
            }
        }
    }

    private final static String LISTEN_CONFIG_KEY = "name";

    private static volatile String listenConfigValue = "";

    public static void main(String[] args) {
        CONFIG_LONG_POLL_EXECUTOR.scheduleAtFixedRate(new ConfigLongPollTask(LISTEN_CONFIG_KEY), 0, 30, TimeUnit.SECONDS);
        System.out.println("开启配置监听 key:" + LISTEN_CONFIG_KEY);
        CONFIG_LONG_POLL_EXECUTOR.scheduleWithFixedDelay(() -> {
            System.out.println(new Date() + " LISTEN_CONFIG_KEY's current value = " + listenConfigValue);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
