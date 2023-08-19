package com.djl;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

public class App {
    public static void main(String[] args) {
        GroovyClassLoader gcl = new GroovyClassLoader();
        String script = "def sayHello(name) { return \"Hello, \" + name + \"!\" }";
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
    }
}
