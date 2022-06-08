package com.djl;

import com.djl.common.MyMessage;
import com.djl.service.HelloService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author djl
 */
public class ClientBizHandler extends SimpleChannelInboundHandler<MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage myMessage) throws Exception {
        System.out.println("接收到服务端 " + ctx.channel().remoteAddress() + " 的消息:" + myMessage);
        final HelloService.RequestFuture requestFuture = HelloService.REQUEST_TABLE.remove(myMessage.getRequestId());
        if (requestFuture == null) {
            System.out.println("requestId:" + myMessage.getRequestId() + " not found future");
            return;
        }
        if (requestFuture.getCallback() != null) {
            requestFuture.getCallback().onResponse(myMessage.getContent());
        } else {
            requestFuture.setResponse(myMessage.getContent());
        }
    }
}
