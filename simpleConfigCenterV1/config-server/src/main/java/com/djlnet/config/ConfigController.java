package com.djlnet.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author djl
 * @create 2022/5/8 9:23
 */
@RestController
@RequestMapping("/config")
@Slf4j
public class ConfigController {

    private final static Map<String, String> CACHE_DATA = new ConcurrentHashMap<>();

    private final static ScheduledExecutorService LONG_POLL_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private final static Queue<ClientLongPollingTask> SCHEDULED_FUTURE_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        CACHE_DATA.put("name", "djl");
        CACHE_DATA.put("age", "18");
    }

    /**
     * 从缓存中获取配置
     *
     * @param configKey
     * @return
     */
    @GetMapping("/get/{configKey}")
    public String get(@PathVariable("configKey") String configKey) {
        return CACHE_DATA.get(configKey);
    }

    @GetMapping("/listen/{configKey}")
    public void listen(@PathVariable("configKey") String configKey,
                       HttpServletRequest request) {
        final AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(0);
        LONG_POLL_EXECUTOR.execute(new ClientLongPollingTask(configKey, asyncContext));
    }

    @PostMapping("/add")
    public String add(@RequestBody @Validated AddConfigReq req) {
        CACHE_DATA.putIfAbsent(req.getConfigKey(), req.getNewValue());
        return "ok";
    }

    @PostMapping("/update")
    public String update(@RequestBody @Validated UpdateConfigReq updateConfigReq) {
        if (!CACHE_DATA.containsKey(updateConfigReq.getConfigKey())) {
            return "not found";
        }
        CACHE_DATA.put(updateConfigReq.getConfigKey(), updateConfigReq.getNewValue());
        LONG_POLL_EXECUTOR.execute(new ConfigDataChangedTask(updateConfigReq.getConfigKey()));
        return "ok";
    }

    private static class ConfigDataChangedTask implements Runnable {

        private final String configKey;

        private ConfigDataChangedTask(String configKey) {
            this.configKey = configKey;
        }

        @Override
        public void run() {
            final Iterator<ClientLongPollingTask> iterator = SCHEDULED_FUTURE_QUEUE.iterator();
            while (iterator.hasNext()) {
                final ClientLongPollingTask task = iterator.next();
                if (!task.configKey.equals(this.configKey)) {
                    continue;
                }
                iterator.remove();
                final Future<?> timeoutFuture = task.getTimeoutFuture();
                if (timeoutFuture != null) {
                    timeoutFuture.cancel(false);
                }
                task.sendResponse(true);
            }

        }
    }

    private static class ClientLongPollingTask implements Runnable {

        private Future<?> timeoutFuture;

        private final String configKey;

        private final AsyncContext asyncContext;

        public ClientLongPollingTask(String configKey, AsyncContext asyncContext) {
            this.configKey = configKey;
            this.asyncContext = asyncContext;
        }


        @Override
        public void run() {
            timeoutFuture = LONG_POLL_EXECUTOR.schedule(() -> {
                final boolean remove = SCHEDULED_FUTURE_QUEUE.remove(ClientLongPollingTask.this);
                if (remove) {
                    sendResponse(false);
                }
            }, 29500, TimeUnit.MILLISECONDS);
            SCHEDULED_FUTURE_QUEUE.add(this);
        }

        void sendResponse(boolean isChanged) {
            if (!isChanged) {
                this.asyncContext.complete();
                return;
            }
            final ServletResponse response = asyncContext.getResponse();
            try {
                response.getWriter().write(String.format("configKey:%s changed", this.configKey));
                asyncContext.complete();
            } catch (IOException e) {
                log.error("time out task run error", e);
                asyncContext.complete();
            }
        }

        public Future<?> getTimeoutFuture() {
            return timeoutFuture;
        }
    }

}
