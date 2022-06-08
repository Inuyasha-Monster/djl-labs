package com.djl.service;

import com.djl.Client;
import com.djl.common.MyMessage;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class HelloService {

    private static final AtomicInteger requestCounter = new AtomicInteger();
    public static final Map<Integer, RequestFuture> REQUEST_TABLE = new ConcurrentHashMap<>();

    @Data
    public static class RequestFuture {
        private volatile boolean requestOK = false;

        private volatile Object response = null;

        private final CountDownLatch latch = new CountDownLatch(1);

        private volatile String errMsg;

        private final ResponseCallback callback;

        RequestFuture(ResponseCallback callback) {
            this.callback = callback;
        }

        public void setResponse(Object o) {
            this.response = o;
            latch.countDown();
        }

        public Object getResponse(long timeout, TimeUnit unit) {
            try {
                latch.await(timeout, unit);
            } catch (InterruptedException ignored) {

            }
            return response;
        }
    }

    public void sayAsync(String msg, ResponseCallback callback) {
        final int requestNo = requestCounter.incrementAndGet();
        final RequestFuture requestFuture = new RequestFuture(callback);
        REQUEST_TABLE.put(requestNo, requestFuture);
        final MyMessage message = new MyMessage(requestNo, msg.trim());
        Client.channel.writeAndFlush(message).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                requestFuture.setRequestOK(true);
            } else {
                requestFuture.setRequestOK(false);
                requestFuture.setErrMsg(future.cause().getMessage());
            }
        });
    }

    public String say(String msg) {
        return this.say(msg, 3000, TimeUnit.MILLISECONDS);
    }

    /**
     * 同步调用获取rpc结果
     *
     * @param msg
     * @return
     */
    public String say(String msg, long timeout, TimeUnit timeUnit) {
        if (msg == null || msg.trim().length() <= 0) {
            return null;
        }
        final int requestNo = requestCounter.incrementAndGet();
        final RequestFuture requestFuture = new RequestFuture(null);
        REQUEST_TABLE.put(requestNo, requestFuture);
        final MyMessage message = new MyMessage(requestNo, msg.trim());
        Client.channel.writeAndFlush(message).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                requestFuture.setRequestOK(true);
            } else {
                requestFuture.setRequestOK(false);
                requestFuture.setErrMsg(future.cause().getMessage());
            }
        });
        final Object response = requestFuture.getResponse(timeout, timeUnit);
        return ((String) response);
    }
}
