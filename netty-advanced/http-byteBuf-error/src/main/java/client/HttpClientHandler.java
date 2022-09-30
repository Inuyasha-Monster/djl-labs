package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.DefaultPromise;

/**
 * @author djl
 */
public class HttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private DefaultPromise<HttpResponse> responsePromise;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        if (msg.decoderResult().isFailure()) {
            throw new Exception(msg.decoderResult().cause());
        }
        HttpResponse response = new HttpResponse(msg);
        responsePromise.setSuccess(response);
    }

    public void setResponsePromise(DefaultPromise<HttpResponse> responsePromise) {
        this.responsePromise = responsePromise;
    }
}
