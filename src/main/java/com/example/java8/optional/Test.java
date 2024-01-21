package com.example.java8.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Test {
    public static void main(String[] args) {

        Optional.of(new Random().nextInt(10))
                .filter(i -> i % 2 == 0)
                .map(i -> "number is even: " + i)
                .ifPresent(System.out::println);

        System.out.println("-----------------------");

        /**
         * Optional 객체 생성방법은 Optional.of, Optional.ofNullable(), stream()의 종료 연산으로 생성할 수 있다.
         * Optional.of()를 사용할 때는 null이 들어갈 수 없다.
         * 어쩌다 null이 들어갈 수도 있다면 Optional.ofNullable()을 사용한다.
          */

        List<String> list = new ArrayList<>();
        list.add("c");
        list.add("b");
        //stream()의 종료 연산
        Optional < String > op1 = list.stream().findFirst();

        //of()
        Optional < String > op2 = Optional.of("a");
        //Optional<String> op2 = Optional.of(null);   //에러

        //ofNullable()
        Optional < String > op3 = Optional.ofNullable(null);


        System.out.println("-----------------------");

        // get()은 Optional안의 값을 반환한다. 하지만 사용을 권장하지 않는다.
        // 왜냐하면 Optional이 null일 경우 런타임 에러가 나기 때문이다.
        // 아래에서 볼 다른 method들은 예외처리도 함께 가능하기에 get()은 되도록 사용하지 않는다.
        Optional<String> op11 = list.stream().findFirst();
        System.out.println(op11.get());
        Optional<String> op31 = Optional.ofNullable(null);
        //op31는 null이므로 NoSuchElementException 발생
        //System.out.println(op31.get());

        System.out.println("-----------------------");

        // ifPresent()는 만약 Optional객체가 null아니면 Consumer가 실행되고 null이면 실행되지 않는다.
        // 아래 코드의 경우 op1은 test가 출력되고 op2는 아무것도 실행되지 않는다.
        Optional<String> opa = Optional.ofNullable("test");
        opa.ifPresent(s -> System.out.println(s));
        Optional<String> opb = Optional.ofNullable(null);
        opb.ifPresent(s -> System.out.println("s"));


        System.out.println("-----------------------");

        // orElse()는 Optional객체가 null이 아니면 값을 가져오고 null이면 Optional의 인자 타입이랑 같은 타입을 반환하게 할 수 있다.
        Optional<String> op111 = Optional.ofNullable("test");
        //Null이 아니므로 test출력
        System.out.println(op111.orElse("orElse"));

        Optional<String> op222 = Optional.ofNullable(null);
        //Null이므로 else출력
        System.out.println(op222.orElse("orElse"));


        System.out.println("-----------------------");

        // orElseGet는 orElse와 비슷하지만 인자로 Supplier를 받아 람다식으로 타입 인자를 반환할 수 있다.
        Optional<String> op = Optional.ofNullable(null);
        //Null이므로 else get을 출력
        System.out.println(op.orElseGet(()-> {
                return "elseGet";
        }));


        System.out.println("-----------------------");

        // optional이 null이면 NoSuchElementException을 던진다.
        Optional<String> ob = Optional.ofNullable(null);
        //ob.orElseThrow();


        System.out.println("-----------------------");

        // optional에도 filter()가 존재하는데
        // filter의 predicate가 true이면 그대로 타입 객체를 포함한 optional이 반환되고
        // false이면 null optional이 반환된다.
        Optional<String> op7 = Optional.ofNullable("a");
        //s.equals("a")는 true이므로 "a"를 포함한 optional객체 반환
        System.out.println(op7.filter(s -> s.equals("a")).get());

        Optional<String> op8 = Optional.ofNullable("a");
        //false이므로 null Optional객체 반환
        System.out.println(op8.filter(s -> s.equals("a")).get());
        System.out.println(
                op8.filter(
                        s -> s.equals("b")
                ).orElse("aaa")
        );



    }
}
