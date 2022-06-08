package com.djl;

import com.djl.common.MyDecoder;
import com.djl.common.MyEncoder;
import com.djl.common.MyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author djl
 */
public class Client {

    private static final ScheduledExecutorService CHECK_EXECUTOR = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("CHECK_EXECUTOR"));

    private static volatile Channel channel;

    public static void main(String[] args) throws InterruptedException {
        final NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            final ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new MyDecoder());
                            pipeline.addLast(new ConnectionHandler());
                            pipeline.addLast(new ClientBizHandler());
                            pipeline.addLast(new MyEncoder());
                        }
                    });
            final int port = 8888;
            final InetSocketAddress remoteAddress = new InetSocketAddress(port);
            final ChannelFuture channelFuture = bootstrap.connect(remoteAddress).sync();
            channel = channelFuture.channel();
            System.out.println("连接远程服务端:" + channel.remoteAddress() + " 成功... 本地连接地址:" + channel.localAddress());

            CHECK_EXECUTOR.scheduleAtFixedRate(() -> {
                if (!channel.isActive()) {
                    try {
                        channel = bootstrap.connect(remoteAddress).sync().channel();
                        System.out.println("重连接成功:" + channel.remoteAddress());
                    } catch (Exception e) {
                        System.out.println("重连接失败:" + e.getMessage());
                    }
                }
            }, 1000, 1000, TimeUnit.MILLISECONDS);

            // 等待用户输入
            Scanner scanner = new Scanner(System.in);
            while (true) {
                final String msg = scanner.nextLine();
                if ("exit".equals(msg)) {
                    break;
                }
                if (msg != null && msg.trim().length() > 0) {
                    channel.writeAndFlush(new MyMessage(msg)).addListener((ChannelFutureListener) channelFuture1 -> {
                        if (channelFuture1.isSuccess()) {
                            System.out.println("client send ok");
                        } else {
                            System.out.println("client send error:" + channelFuture1.cause().getMessage());
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.closeFuture().sync();
            }
            workerGroup.shutdownGracefully();
            CHECK_EXECUTOR.shutdown();
        }
    }
}
