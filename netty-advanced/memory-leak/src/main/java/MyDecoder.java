import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author djl
 */
public class MyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        // 暂存读指针
        byteBuf.markReaderIndex();
        final int length = byteBuf.readInt();
        // 剩余可读长度<length，则回拨读指针，说明socket缓存区还没准备好
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        final String json = new String(bytes, StandardCharsets.UTF_8);
        final MyMessage msg = JSON.parseObject(json, MyMessage.class);
        list.add(msg);
    }
}
