import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author djl
 */
public class MyEncoder extends MessageToByteEncoder<MyMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMessage myMessage, ByteBuf byteBuf) throws Exception {
        final String json = JSON.toJSONString(myMessage);
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        // 设置消息字节长度
        byteBuf.writeInt(bytes.length);
        // 设置消息内容
        byteBuf.writeBytes(bytes);
    }
}
