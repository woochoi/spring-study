package com.example.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    //System.out.println(sortedList);

    // Stream<Integer> --> IntStream, LongStream, DoubleStream .. 오토박싱/언박싱의 비효울 제거
    // 1 -> new Integer(1) : "기본형" 에서 참조형으로 바뀜
    Stream<String> strStream1 = Stream.of("dd", "aaa", "CC", "cc", "b");
    int sum = strStream1.parallel()
            .mapToInt(s -> s.length()).sum();

    //System.out.println(sum);

    // 다음 강의 : https://www.youtube.com/watch?v=AOw4cCVUJC4&list=PLW2UjW795-f6xWA2_MUhEVgPauhGl3xIp&index=164


    // 내일 : https://cjw-awdsd.tistory.com/41
    // FlatMap
    //<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
    //flatMap은 중첩 구조를 한 단계 제거하고 하나의 Collection으로 풀어주는 역할을 한다.\
    //List안에 List를 넣은 이중 list를 flatMap을 사용해 하나의 list로 풀어 처리하는 코드

    List<String> list1 = new ArrayList<>();
    list1.add("a");
    list1.add("b");
    list1.add("c");
    list1.add("d");

    List<List<String>> flat = new ArrayList < > ();
    flat.add(list1);
    flat.add(list1);
    flat.add(list1);

    flat.stream()
          .flatMap(strings -> strings.stream())
          .filter(s -> s.equals("a"))
          .forEach(System.out::println);

    // a a a

    System.out.println("-----------------------");

    //flatMap에 메소드 레퍼런스 사용
    flat.stream()
          .flatMap(Collection::stream)
          .filter(s -> s.equals("a"))
          .forEach(System.out::println);

    // a a a

    System.out.println("-----------------------");

    // reduce를 사용하면 인자가 integer, integer2인 걸 볼 수 있는데
    // 이것은 요소를 거치면서 integer가 전에 수행된 연산의 값을 가지게 된다.
    // 위 코드로 봤을 때는 처음 연산이 integer + integer2라면 그 다음의 인자는 reduce(지금까지의 총 합, 다음 인자)이 될 것이다.
    // reduce의 첫번째 인자에 초기값을 선언하면 초기값부터 연산이 시작된다.
    List<Integer> list2 = new ArrayList<> ();
    list2.add(1);
    list2.add(2);
    list2.add(3);
    list2.add(4);
    Integer test = list2.stream().reduce(4, (integer, integer2) -> integer + integer2);
    System.out.println(test);
    System.out.println(list2.stream().reduce((integer, integer2) -> integer + integer2).get());

    }
}
