import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux.range(1, 100)
                .parallel(4)
                .runOn(Schedulers.parallel())
                .doOnNext(x -> {
                    System.out.println(Thread.currentThread().getName() + " x = " + x);
                })
                .map(String::valueOf)
                .subscribe(x -> System.out.println(Thread.currentThread().getName() + " x1 = " + x), ex -> System.out.println("ex = " + ex), latch::countDown);
        latch.await();
    }
}
