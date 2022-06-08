package com.djl.server;

import com.djl.common.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class ServerBizHandler extends SimpleChannelInboundHandler<MyMessage> {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage myMessage) throws Exception {
        System.out.println("接收到 " + ctx.channel().remoteAddress() + " 消息 myMessage = " + myMessage.getContent());

        final MyMessage response = new MyMessage("server push msg num:" + COUNTER.incrementAndGet());
        // ctx.writeAndFlush从当前handler向前找outHandler执行
        // ctx.channel().writeAndFlush从tailHandler向前找outHandler执行
        ctx.channel().writeAndFlush(response).addListener(channelFuture -> {
            if (channelFuture.isSuccess()) {
                System.out.println("server push message ok");
            } else {
                System.out.println("server push message error" + channelFuture.cause().getMessage());
            }
        });
    }
}
