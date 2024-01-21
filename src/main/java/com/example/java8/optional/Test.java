package com.example.java8.optional;

import java.util.Optional;
import java.util.Random;

public class Test {
    public static void main(String[] args) {

        Optional.of(new Random().nextInt(10))
                .filter(i -> i % 2 == 0)
                .map(i -> "number is even: " + i)
                .ifPresent(System.out::println);

    }
}
