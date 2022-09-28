import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class MemoryLeakServerHandler extends ChannelInboundHandlerAdapter {

    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(1);

    static {
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            System.out.println(LocalDateTime.now() + " COUNTER = " + COUNTER.get());
        }, 0, 3, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //final ByteBuf reqMsg = (ByteBuf) msg;
        COUNTER.incrementAndGet();
        //super.channelRead(ctx, msg);
        //ReferenceCountUtil.release(reqMsg);
        //if (COUNTER.longValue() % 1000 == 0) {
        //    System.out.println("reqMsg = " + reqMsg);
        //}
    }
}
