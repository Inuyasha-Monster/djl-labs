package com.djl;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.apache.commons.lang3.time.StopWatch;

import javax.script.*;
import java.util.concurrent.TimeUnit;

public class ScriptEngineTest3_1 {

    public static void main(String[] args) throws ScriptException, InterruptedException {

        ScriptEngineManager sm = new ScriptEngineManager();

        NashornScriptEngineFactory factory = null;
        for (ScriptEngineFactory f : sm.getEngineFactories()) {
            if (f.getEngineName().equalsIgnoreCase("Oracle Nashorn")) {
                factory = (NashornScriptEngineFactory)f;
                break;
            }
        }

        String[] stringArray = new String[]{"-doe", "--global-per-engine"};
        ScriptEngine scriptEngine = factory.getScriptEngine(stringArray);

        final Compilable compilable = (Compilable) scriptEngine;

        // 定义一个JavaScript函数
        String script = "function add(a, b) { return a + b; }; add(a,b);";

        final CompiledScript compiledScript = compilable.compile(script);

        StopWatch stopWatch = new StopWatch("测试ScriptEngine性能");
        stopWatch.start();
        for (int i = 0; i < 10000; i++) {
            // 创建一个Bindings对象，用于存储参数
            Bindings bindings = new SimpleBindings();

            final int a = i + 1;
            bindings.put("a", a);

            final int b = i + 2;
            bindings.put("b", b);

            // 执行JavaScript函数，并传递参数
            Object result = compiledScript.eval(bindings);
            final String format = String.format("%s + %s = %s", a, b, result);
            // 输出结果
            System.out.println(format);
        }
        stopWatch.stop();
        final long MILLISECONDS = stopWatch.getTime(TimeUnit.MILLISECONDS);
        System.out.println("MILLISECONDS = " + MILLISECONDS); // 4281ms -> 546ms
    }
}
