package com.djl;

import org.apache.commons.lang3.time.StopWatch;

import javax.script.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScriptEngineTest {

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");

    public static void main(String[] args) throws ScriptException, InterruptedException {

        final Compilable compilable = (Compilable) SCRIPT_ENGINE;

        // 定义一个JavaScript函数
        String script = "function add(a, b) { return a + b; }; add(a,b);";

        final CompiledScript compiledScript = compilable.compile(script);

        final ExecutorService threadPool = Executors.newCachedThreadPool();

        AtomicInteger counter = new AtomicInteger();
        StopWatch stopWatch = new StopWatch("测试ScriptEngine性能");
        stopWatch.start();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                // 创建一个Bindings对象，用于存储参数
                Bindings bindings = new SimpleBindings();

                final int a = finalI + 1;
                bindings.put("a", a);

                final int b = finalI + 2;
                bindings.put("b", b);

                // 执行JavaScript函数，并传递参数
                Object result = null;
                try {
                    result = compiledScript.eval(bindings);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }

                final String format = String.format("%s + %s = %s", a, b, result);

                // 输出结果
                System.out.println(format);

                counter.incrementAndGet();
            });
        }
        threadPool.shutdown();
        final boolean awaitTermination = threadPool.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("awaitTermination = " + awaitTermination);
        stopWatch.stop();
        System.out.println("counter = " + counter.get());

        final long seconds = stopWatch.getTime(TimeUnit.MILLISECONDS);
        System.out.println("seconds = " + seconds);
    }
}
