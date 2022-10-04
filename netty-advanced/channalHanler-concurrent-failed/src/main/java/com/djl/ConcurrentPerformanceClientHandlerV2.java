package com.djl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author djl
 */
public class ConcurrentPerformanceClientHandlerV2 extends SimpleChannelInboundHandler<MyMessage> {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("本地地址：" + ctx.channel().localAddress() + " 与server建立连接: " + ctx.channel().remoteAddress());
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ctx.writeAndFlush(new MyMessage(0, "hello"));
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {

    }
}
