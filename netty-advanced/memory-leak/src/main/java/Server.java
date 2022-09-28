import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author djl
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            // 如果增加解码则不会有堆外内存泄露的问题，因为解码逻辑内部包含了对ByteBuf的释放
                            //pipeline.addLast(new MyDecoder());
                            pipeline.addLast(new MemoryLeakServerHandler());
                            //pipeline.addLast(new MyEncoder());
                        }
                    });
            final int port = 8888;
            final ChannelFuture channelFuture = b.bind(port).sync();
            System.out.println("server listened port: " + port);
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
