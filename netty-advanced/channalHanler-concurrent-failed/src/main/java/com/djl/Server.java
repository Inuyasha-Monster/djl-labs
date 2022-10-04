package com.djl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author djl
 */
public class Server {

    static final EventExecutorGroup EVENT_EXECUTOR_GROUP = new DefaultEventExecutorGroup(100);

    //private static final ConcurrentPerformanceServerHandler serverHandler = new ConcurrentPerformanceServerHandler();

    public static void main(String[] args) throws InterruptedException {
        final NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        final NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MyDecoder());
                            //ch.pipeline().addLast(new ConcurrentPerformanceServerHandlerV2());
                            ch.pipeline().addLast(EVENT_EXECUTOR_GROUP, new ConcurrentPerformanceServerHandler());
                        }
                    });
            final ChannelFuture channelFuture = b.bind(8888).sync();
            System.out.println("server listen port = 8888 ok");
            channelFuture.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
