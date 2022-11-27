package com.djl.redis;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author djl
 */
@ChannelHandler.Sharable
public class Encoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        // 将直观的字符串cmd翻译为对应的Arrays格式的cmd

        // 将redis client的字符串cmd编码UTF8的二进制数据发送给redis
    }
}
