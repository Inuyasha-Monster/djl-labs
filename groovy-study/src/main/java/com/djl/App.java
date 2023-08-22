package com.djl;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws InstantiationException, IOException, IllegalAccessException {
        GroovyClassLoader gcl = new GroovyClassLoader();
        String script = "def function(Map map) {\n" +
                "    System.exit(0);\n" +
                "    return map;\n" +
                "}";
        Class<?> groovyClass = gcl.parseClass(script);

        //Class<?> groovyClass1 = gcl.parseClass(script);
        // 输出false
        //System.out.println(groovyClass == groovyClass1);

        try {
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            Object result = groovyObject.invokeMethod("sayHello", "World");
            System.out.println(result);  // 输出 "Hello, World!"
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String mapScript = "import org.apache.commons.lang3.StringUtils\n" +
                "\n" +
                "// 通过java将传输参数封装为map的格式传递到脚本中\n" +
                "Map handle(Map map) {\n" +
                "    if (StringUtils.isNotBlank(map[\"value\"] as CharSequence)) {\n" +
                "        map[\"value\"] = map[\"value\"] + 1;\n" +
                "    }\n" +
                "    return map;\n" +
                "}";
        //for (int i = 0; i < 100000; i++) {
        final Class<?> mapClass = gcl.parseClass(mapScript);
        //gcl.clearCache();
        //gcl.close();
        //}

        Map<String, String> map = new HashMap<>();
        map.put("value", "  ");

        final Object handleResult = ((GroovyObject) mapClass.newInstance()).invokeMethod("handle", map);
        @SuppressWarnings("unchecked") final Map<String, String> result = (Map<String, String>) handleResult;

        System.out.println("result = " + result);
    }
}
