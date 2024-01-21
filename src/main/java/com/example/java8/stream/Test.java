package com.example.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

    List<Integer> list = Arrays.asList(6,3,1,4,5);

    Stream<Integer> intStream = list.stream();
    Stream<String> strStream = Stream.of(new String[]{"a",  "b" , "c"});
    Stream<Integer> evenStream = Stream.iterate(0, n->n+1);

    List<Integer> sortedList = list.stream()
            .sorted()
            .collect(Collectors.toList());

    System.out.println(sortedList);

    // Stream<Integer> --> IntStream, LongStream, DoubleStream .. 오토박싱/언박싱의 비효울 제거
    // 1 -> new Integer(1) : "기본형" 에서 참조형으로 바뀜
    Stream<String> strStream1 = Stream.of("dd", "aaa", "CC", "cc", "b");
    int sum = strStream1.parallel()
            .mapToInt(s -> s.length()).sum();

    System.out.println(sum);

    /* 다음 강의 :
        https://www.youtube.com/watch?v=AOw4cCVUJC4&list=PLW2UjW795-f6xWA2_MUhEVgPauhGl3xIp&index=164
    */





    }
}
