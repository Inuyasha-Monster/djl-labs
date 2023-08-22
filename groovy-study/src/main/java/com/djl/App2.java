package com.djl;

import javax.script.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App2 {

    private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("nashorn");

    public static void main(String[] args) throws InstantiationException, IOException, IllegalAccessException, ScriptException {
        final Compilable compilable = (Compilable) SCRIPT_ENGINE;

        CompiledScript script = compilable.compile("function add(a, b) { return a + b; }");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Object eval = null;
                try {
                    Map<String, Object> map = new HashMap<>();
                    map.put("a", 1);
                    map.put("b", 2);
                    eval = script.eval(new SimpleBindings(map));
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                System.out.println("eval = " + eval);
            }).start();
        }
    }
}
