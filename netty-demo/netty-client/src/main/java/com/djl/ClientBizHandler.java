package com.djl;

import com.djl.common.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author djl
 */
public class ClientBizHandler extends SimpleChannelInboundHandler<MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage myMessage) throws Exception {
        System.out.println("接收到服务端 " + ctx.channel().remoteAddress() + " 的消息:" + myMessage);
    }
}
