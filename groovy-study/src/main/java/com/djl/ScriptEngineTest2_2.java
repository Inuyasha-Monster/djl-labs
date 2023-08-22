package com.djl;

import org.apache.commons.lang3.time.StopWatch;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScriptEngineTest2_2 {

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");

    public static void main(String[] args) throws ScriptException, InterruptedException, NoSuchMethodException {

        // 定义一个JavaScript函数
        String script = "function add(a, b) { return a + b; }";

        SCRIPT_ENGINE.eval(script);
        final Invocable invocable = (Invocable) SCRIPT_ENGINE;

        final ExecutorService executorService = Executors.newFixedThreadPool(8);

        StopWatch stopWatch = new StopWatch("测试ScriptEngine性能");
        stopWatch.start();
        for (int i = 0; i < 10000; i++) {

            int finalI = i;

            executorService.execute(() -> {
                final int a = finalI + 1;
                final int b = finalI + 2;
                Object result = null;
                try {
                    result = invocable.invokeFunction("add", a, b);
                } catch (ScriptException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                final String format = String.format("%s + %s = %s", a, b, result);
                // 输出结果
                System.out.println(format);
            });
        }

        executorService.shutdown();
        final boolean awaitTermination = executorService.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("awaitTermination = " + awaitTermination);

        stopWatch.stop();
        final long MILLISECONDS = stopWatch.getTime(TimeUnit.MILLISECONDS);
        System.out.println("MILLISECONDS = " + MILLISECONDS); // 254ms左右 -> 385ms
    }
}
