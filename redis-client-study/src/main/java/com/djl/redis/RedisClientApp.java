package com.djl.redis;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;

import java.util.Scanner;

/**
 * @author djl
 */
public class RedisClientApp {
    public static void main(String[] args) throws InterruptedException {
        // 配置netty client连接redis-server
        Bootstrap b = new Bootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new RedisDecoder());
                            pipeline.addLast(new RedisBulkStringAggregator());
                            pipeline.addLast(new RedisArrayAggregator());
                            pipeline.addLast(new RedisEncoder());
                            pipeline.addLast(new RedisClientHandler());
                        }
                    });
            final Channel channel = b.connect("localhost", 6379).sync().channel();
            System.out.println("connected redis-server: " + channel.remoteAddress().toString() + " ok,local address: " + channel.localAddress().toString());
            Scanner scanner = new Scanner(System.in);
            System.out.println("please input your redis client cmd");
            while (true) {
                final String line = scanner.nextLine();
                if (line == null || "".equals(line) || "exit".equals(line)) {
                    channel.close().sync();
                    System.out.println("exit ok");
                    return;
                }
                channel.writeAndFlush(line).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            System.out.printf("cmd send error:%s \r\n", future.cause().getMessage());
                        }
                    }
                });
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
