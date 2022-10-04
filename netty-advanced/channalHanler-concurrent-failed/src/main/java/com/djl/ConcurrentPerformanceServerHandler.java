package com.djl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class ConcurrentPerformanceServerHandler extends SimpleChannelInboundHandler<MyMessage> {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private final static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    static {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            final int qps = counter.getAndSet(0);
            System.out.println("the server qps = " + qps);
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("与client建立连接: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
        counter.incrementAndGet();
        Random random = new Random();
        // 模拟业务逻辑处理时长
        TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
    }
}
