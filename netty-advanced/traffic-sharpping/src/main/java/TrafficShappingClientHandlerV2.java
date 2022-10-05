import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class TrafficShappingClientHandlerV2 extends ChannelInboundHandlerAdapter {

    private final AtomicInteger SEQ = new AtomicInteger(0);

    private static final byte[] ECHO_REQ = new byte[1024 * 1024];

    private static final String DELIMTER = "$_";

    private final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            ByteBuf byteBuf = null;
            for (int i = 0; i < 10; i++) {
                byteBuf = Unpooled.copiedBuffer(ECHO_REQ, DELIMTER.getBytes(StandardCharsets.UTF_8));
                SEQ.getAndAdd(byteBuf.readableBytes());
                if (ctx.channel().isWritable()) {
                    ctx.write(byteBuf);
                }
            }
            ctx.flush();
            final int counter = SEQ.getAndSet(0);
            System.out.println(LocalDateTime.now() + " local add = " + ctx.channel().localAddress() + " the client send rate is = " + counter + " bytes/s");
        }, 0, 1, TimeUnit.SECONDS);
    }
}
