import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author djl
 */
@ChannelHandler.Sharable
public class SimpleServerHandler extends SimpleChannelInboundHandler<MyMessage> {

    private final static LongAdder counter = new LongAdder();

    private final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(1);

    static {
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            System.out.println("counter = " + counter);
        }, 0, 3, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
        counter.increment();
    }
}
