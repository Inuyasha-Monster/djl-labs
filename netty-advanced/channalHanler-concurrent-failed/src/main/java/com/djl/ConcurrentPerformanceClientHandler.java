package com.djl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author djl
 */
public class ConcurrentPerformanceClientHandler extends SimpleChannelInboundHandler<MyMessage> {

    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("与server建立连接：" + ctx.channel().remoteAddress());
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            for (int i = 0; i < 100; i++) {
                ctx.writeAndFlush(new MyMessage(i, "hello" + i));
            }
            System.out.println(LocalDateTime.now() + " 100个消息发送完成");
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {

    }
}
