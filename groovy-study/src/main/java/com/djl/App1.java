package com.djl;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App1 {
    public static void main(String[] args) throws InstantiationException, IOException, IllegalAccessException {
        GroovyClassLoader gcl = new GroovyClassLoader();

        String mapScript = "import com.djl.User\n" +
                "\n" +
                "import java.util.function.Consumer\n" +
                "import java.util.function.Predicate\n" +
                "import java.util.stream.Collectors\n" +
                "\n" +
                "// 通过java将传输参数封装为map的格式传递到脚本中\n" +
                "List<User> handle(List<User> list, int a) {\n" +
                "    if (list == null || list.isEmpty()) {\n" +
                "        return Collections.emptyList();\n" +
                "    }\n" +
                "    def result = list.stream().filter(new Predicate<User>() {\n" +
                "        @Override\n" +
                "        boolean test(User user) {\n" +
                "            return user.getAge() > 18;\n" +
                "        }\n" +
                "    }).collect(Collectors.toList())\n" +
                "    result.forEach(new Consumer<User>() {\n" +
                "        @Override\n" +
                "        void accept(User user) {\n" +
                "            user.setAge(user.getAge() + a);\n" +
                "        }\n" +
                "    })\n" +
                "    return result;\n" +
                "}";

        final Class<?> mapClass = gcl.parseClass(mapScript);

        List<User> users = new ArrayList<>();
        users.add(new User("djl",28));
        users.add(new User("djl1",12));
        users.add(new User("djl2",19));

        List<Object> objects = new ArrayList<>();
        objects.add(users);
        objects.add(2);

        final Object handleResult = ((GroovyObject) mapClass.newInstance()).invokeMethod("handle", objects);
        @SuppressWarnings("unchecked") final List<User> list = (List<User>) handleResult;
        System.out.println("list = " + list);
    }
}
