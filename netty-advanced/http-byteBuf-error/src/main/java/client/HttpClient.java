package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.DefaultPromise;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * @author djl
 */
public class HttpClient {

    private Channel channel;
    private final HttpClientHandler handler = new HttpClientHandler();

    private void connect(String host, int port) throws InterruptedException {
        final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        final ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 2));
                        pipeline.addLast(handler);
                    }
                });
        final ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        this.channel = channelFuture.channel();
    }

    private HttpResponse blockSend(FullHttpRequest request) throws ExecutionException, InterruptedException {
        //request.headers().set("Connection", "keep-alive");
        //request.headers().set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");


        // 关键请求头，不加的话服务端直接认为错误请求，而且server端会检查该值
        request.headers().set(HttpHeaders.Names.HOST, "localhost:8080");

        //request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);

        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        DefaultPromise<HttpResponse> promise = new DefaultPromise<>(channel.eventLoop());
        this.handler.setResponsePromise(promise);
        this.channel.writeAndFlush(request);
        final HttpResponse httpResponse = promise.get();
        if (httpResponse != null) {
            System.out.println("httpResponse body = " + new String(httpResponse.body(), StandardCharsets.UTF_8));
        }
        return httpResponse;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, URISyntaxException {
        HttpClient client = new HttpClient();
        client.connect("localhost", 8080);
        // 分配的是：非池化的堆内内存
        final ByteBuf byteBuf = Unpooled.wrappedBuffer("Http Message!".getBytes(StandardCharsets.UTF_8));
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, new URI("http://localhost:8080/test").toASCIIString(), byteBuf);
        final HttpResponse httpResponse = client.blockSend(request);
        System.out.println("httpResponse = " + httpResponse);
    }
}
