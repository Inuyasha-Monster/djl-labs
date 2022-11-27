package com.djl.redis;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author djl
 */
public class RedisClientApp {
    public static void main(String[] args) throws InterruptedException {

        Decoder decoder = new Decoder();
        Encoder encoder = new Encoder();

        // 配置netty client连接redis-server
        Bootstrap b = new Bootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(decoder).addLast(encoder);
                        }
                    });
            final Channel channel = b.connect("localhost", 6379).sync().channel();
            System.out.println("connected redis-server: " + channel.remoteAddress().toString() + " ok,local address: " + channel.localAddress().toString());
            Scanner scanner = new Scanner(System.in);
            System.out.println("please input your redis client cmd");
            while (true) {
                final String line = scanner.nextLine();
                if (line == null || "".equals(line) || "exit".equals(line)) {
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
