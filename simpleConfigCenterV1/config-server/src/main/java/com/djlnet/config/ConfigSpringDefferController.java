package com.djlnet.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author djl
 */
@RestController
@RequestMapping("/config/spring")
public class ConfigSpringDefferController {

    /**
     * 客户端长轮训存根
     */
    private final static Queue<ClientLongPollItem> CLIENT_SUBS = new ConcurrentLinkedQueue<>();

    private final static Map<String, String> CACHE_DATA = new ConcurrentHashMap<>();

    private final static ScheduledExecutorService LONG_POLL_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    static {
        CACHE_DATA.put("name", "djl");
        CACHE_DATA.put("age", "18");
    }

    @Data
    @AllArgsConstructor
    private static class ClientLongPollItem {
        private String configKey;
        private DeferredResult<String> deferredResult;
    }

    @PostMapping("/update")
    public String update(@RequestBody @Validated UpdateConfigReq updateConfigReq) {
        if (!CACHE_DATA.containsKey(updateConfigReq.getConfigKey())) {
            return "not found configKey";
        }
        CACHE_DATA.put(updateConfigReq.getConfigKey(), updateConfigReq.getNewValue());
        LONG_POLL_EXECUTOR.execute(() -> {
            final Iterator<ClientLongPollItem> iterator = CLIENT_SUBS.iterator();
            while (iterator.hasNext()) {
                final ClientLongPollItem pollItem = iterator.next();
                if (!pollItem.configKey.equals(updateConfigReq.getConfigKey())) {
                    continue;
                }
                iterator.remove();
                final DeferredResult<String> deferredResult = pollItem.getDeferredResult();
                if (!deferredResult.isSetOrExpired()) {
                    deferredResult.setResult(String.format("configKey:%s changed", pollItem.getConfigKey()));
                }
            }
        });
        return "ok";
    }

    @GetMapping("/listen/{configKey}")
    public DeferredResult<String> listen(@PathVariable("configKey") String configKey) {
        final DeferredResult<String> deferredResult = new DeferredResult<>(29500L, "");
        final ClientLongPollItem pollItem = new ClientLongPollItem(configKey, deferredResult);
        CLIENT_SUBS.add(pollItem);
        deferredResult.onTimeout(() -> CLIENT_SUBS.remove(pollItem));
        return deferredResult;
    }
}
