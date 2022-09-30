import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(workerGroup)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 1024 * 1024 * 10)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        final ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new MyEncoder());
                    }
                });
        final int port = 8888;
        final InetSocketAddress remoteAddress = new InetSocketAddress(port);
        final ChannelFuture channelFuture = bootstrap.connect(remoteAddress).sync();
        final Channel channel = channelFuture.channel();
        System.out.println("连接远程服务端:" + channel.remoteAddress() + " 成功... 本地连接地址:" + channel.localAddress());
        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                final SocketAddress localAddress = future.channel().localAddress();
                System.out.println("断开链接 localAddress = " + localAddress + " remoteAddress = " + future.channel().remoteAddress());
            }
        });


        AtomicInteger count = new AtomicInteger();

        while (true) {
            if (channel.isWritable()) {
                final ChannelFuture future = channel.writeAndFlush(new MyMessage(1, UUID.randomUUID().toString()));
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            System.out.println("error cnt get = " + count.incrementAndGet());
                        }
                    }
                });
            } else {
                System.out.println("send queue is busy: " + channel.unsafe().outboundBuffer().nioBufferSize());
            }
        }

    }
}
