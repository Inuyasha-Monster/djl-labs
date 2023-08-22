package com.djl

import com.djl.User

import java.util.function.Consumer
import java.util.function.Predicate
import java.util.stream.Collectors

// 通过java将传输参数封装为map的格式传递到脚本中
List<User> handle(List<User> list, int a) {
    if (list == null || list.isEmpty()) {
        return Collections.emptyList();
    }
    def result = list.stream().filter(new Predicate<User>() {
        @Override
        boolean test(User user) {
            return user.getAge() > 18;
        }
    }).collect(Collectors.toList())
    result.forEach(new Consumer<User>() {
        @Override
        void accept(User user) {
            user.setAge(user.getAge() + a);
        }
    })
//    System.exit(0);



    return result;
}
