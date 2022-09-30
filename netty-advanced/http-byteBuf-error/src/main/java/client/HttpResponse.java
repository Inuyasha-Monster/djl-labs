package client;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * @author djl
 */
public class HttpResponse {
    private final HttpHeaders header;
    private final FullHttpResponse response;
    private final byte[] body;

    public HttpResponse(FullHttpResponse response) {
        this.response = response;
        this.header = response.headers();
        final byte[] bytes = new byte[this.response.content().readableBytes()];
        this.response.content().getBytes(0, bytes);
        body = bytes;
    }

    public byte[] body() {
        return body;
    }
}
