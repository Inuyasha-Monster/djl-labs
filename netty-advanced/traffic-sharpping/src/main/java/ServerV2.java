import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author djl
 */
public class ServerV2 {

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
                            ch.pipeline().addLast(new ChannelTrafficShapingHandler(1024 * 1024, 1024 * 1024, 1000));
                            final ByteBuf delimter = Unpooled.copiedBuffer("$_".getBytes(StandardCharsets.UTF_8));
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(2048 * 1024, delimter));
                            ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
                            ch.pipeline().addLast(new TrafficShappingServerHandlerV2());
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
