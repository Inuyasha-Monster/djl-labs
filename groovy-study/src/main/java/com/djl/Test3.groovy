package com.djl

import com.djl.User

import java.util.function.Predicate
import java.util.stream.Collectors

def handle(List<User> list, Integer age) {
    if (list == null || list.isEmpty()) {
        return Collections.emptyList();
    }
    def result = list.stream().filter(new Predicate<User>() {
        @Override
        boolean test(User user) {
            return user.getAge() > age;
        }
    }).collect(Collectors.toList())
    return result;
}
