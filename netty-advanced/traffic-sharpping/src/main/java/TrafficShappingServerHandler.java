import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TrafficShappingServerHandler extends ChannelInboundHandlerAdapter {

    private final AtomicInteger counter = new AtomicInteger(0);

    static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public TrafficShappingServerHandler() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println(LocalDateTime.now() + " the server receive client rate is = " + counter.getAndSet(0) + " bytes/s");
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        counter.addAndGet(((String) msg).getBytes(StandardCharsets.UTF_8).length);
    }
}
