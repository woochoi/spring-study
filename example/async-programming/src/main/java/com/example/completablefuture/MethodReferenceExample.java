package com.example.completablefuture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
public class MethodReferenceExample {
    @RequiredArgsConstructor
    public static class Person {
        @Getter
        private final String name;

        public Boolean compareTo(Person o) {
            return o.name.compareTo(name) > 0;
        }
    }

    // static method
    public static void print(String name) {
        System.out.println(name);
    }

    // :: 연산자를 이용해서 함수에 대한 참조를 간결하게 표현
    // method reference
    // static method reference
    // instance method reference
    // constructor method reference

    public static void main(String[] args) {
        var target = new Person("f");
        // method reference 는 Consumer 로도 된다!
        Consumer<String> staticPrint = MethodReferenceExample::print;

        Stream.of("a", "b", "g", "h")
                .map(Person::new)// constructor reference
                .filter(target::compareTo) // method reference
                .map(Person::getName) // instance method reference
                .forEach(System.out::println); // static method reference
    }
}
