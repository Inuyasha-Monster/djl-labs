package com.djl;

import javax.script.*;
import java.io.IOException;

public class App3 {
    public static void main(String[] args) throws InstantiationException, IOException, IllegalAccessException, ScriptException, NoSuchMethodException {
        // 创建一个ScriptEngineManager对象
        ScriptEngineManager manager = new ScriptEngineManager();
        // 获取JavaScript的执行引擎
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // 定义一个JavaScript函数
        String script = "function add(a, b) { return a + b; }";

        // 编译JavaScript函数
        CompiledScript compiledScript = ((Compilable) engine).compile(script);
        compiledScript.eval();

        // 模拟web代码编辑器修改js代码
        script = "function add(a, b) { return a - b; }";

        // 编译JavaScript函数
        CompiledScript compiledScript2 = ((Compilable) engine).compile(script);

        // 执行编译后的JavaScript函数
        compiledScript2.eval();

        // 获取JavaScript函数
        Invocable invocable = (Invocable) engine;
        // 调用函数并传递参数
        Object result = invocable.invokeFunction("add", 5, 3);

        // 输出结果
        System.out.println(result);  // 输出: 2
    }
}
