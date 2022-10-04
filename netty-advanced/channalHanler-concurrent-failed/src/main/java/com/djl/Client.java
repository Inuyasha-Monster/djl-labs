package com.djl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author djl
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyEncoder());
                        ch.pipeline().addLast(new ConcurrentPerformanceClientHandler());
                    }
                });
        final ChannelFuture channelFuture = b.connect("localhost", 8888).sync();
        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
