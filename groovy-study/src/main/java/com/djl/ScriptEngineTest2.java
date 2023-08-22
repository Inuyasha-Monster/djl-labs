package com.djl;

import org.apache.commons.lang3.time.StopWatch;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.concurrent.TimeUnit;

public class ScriptEngineTest2 {

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");

    public static void main(String[] args) throws ScriptException, InterruptedException, NoSuchMethodException {

        // 定义一个JavaScript函数
        String script = "function add(a, b) { return a + b; }";

        SCRIPT_ENGINE.eval(script);
        final Invocable invocable = (Invocable) SCRIPT_ENGINE;

        StopWatch stopWatch = new StopWatch("测试ScriptEngine性能");
        stopWatch.start();
        for (int i = 0; i < 10000; i++) {
            final int a = i + 1;
            final int b = i + 2;
            final Object result = invocable.invokeFunction("add", a, b);
            final String format = String.format("%s + %s = %s", a, b, result);
            // 输出结果
            System.out.println(format);
        }
        stopWatch.stop();
        final long MILLISECONDS = stopWatch.getTime(TimeUnit.MILLISECONDS);
        System.out.println("MILLISECONDS = " + MILLISECONDS); // 254ms左右
    }
}
