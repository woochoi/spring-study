package com.example.java8.lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Test {

    // https://www.devkuma.com/docs/java/lamda/#functiont-r
    // 함수형 인터페이스(Functional Interface)는 추상 메서드가 딱 하나만 존재하는 인터페이스를 말한다.
    // 즉, 함수를 1급 객체처럼 다룰 수 있게 해주는 어노테이션으로 인터페이스에 선언하여 단 하나의 추상 메소드만을 갖도록 제한하는 역할을 한다.
    public static void main(String[] args) {
        Math plus = (first, second) -> first + second;
        //System.out.println(plus.calc(3, 2));
        Math minus = (first, second) -> first - second;
        //System.out.println(minus.calc(3, 2));

        // Function
        Function<String, Integer> function = str -> str.length();
        int length = function.apply("Hello World!");
        System.out.println(length);

        // Consumer
        // 먼저 accept로 받아들인 Consumer를 먼저 처리하여 “Hello"가 표시되고,
        // andThen으로 받은 두 번째 Consumer를 처리하여 “Hello World!“가 표시
        Consumer<String> consumer = (str) -> System.out.println(str.split(" ")[0]);
        consumer.andThen(System.out::println).accept("Hello World!");

        // Supplier
        Supplier<String> supplier = () -> "Hello World!";
        System.out.println(supplier.get());

        // Predicate
        Predicate<String> predicate = (str) -> str.equals("Hello World!");
        boolean aaa = predicate.test("Hello World!");
        System.out.println(aaa);

        //////////////////////////////////////////////////////////////////////////


        TestMethod testmethod = new TestMethod();

        //static 메소드를 Function에 등록
        //test()는 String인자를 받고 String을 반환한다. -> Function<String, String>같음 ->유추가능
        Function <String, String> function1 = TestMethod::test;
        System.out.println(function1.apply("static test"));

        //인자가 없는 test객체의 voidTest()를 등록
        Consumer <String> consumer1 = testmethod::voidTest;
        consumer.accept("void test");

        //인자가 있는 test객체의 stringTest()를 등록
        Function <String, String> function2 = testmethod::stringTest;
        System.out.println(function2.apply("return test"));

        //인자가 한개 있는 생성자 등록
        Function <String, TestMethod> function3 = TestMethod::new;
        System.out.println(function3.apply("new has param test").getTestStr());

        //인자가 없는 기본 생성자 등록
        Supplier <TestMethod> supplier1 = TestMethod::new;
        System.out.println(supplier1.get().getTestStr());

    }

    // 함수형 인터페이스 4가지 : 총 4가지 함수형 인터페이스가 존재
    //함수형 인터페이스	    매개 변수	    반환값
    //Function<T, R>	O	        O
    //Consumer<T>	    O	        X
    //Supplier<T>	    X	        O
    //Predicate<T>	    O	        Boolean

    //함수형 인터페이스	    Descripter	        Method
    //Predicate	        T -> boolean	    boolean test(T t)
    //Consumer	        T -> void	        void accept(T t)
    //Supplier	        () -> T	            T get()
    //Function<T, R>	T -> R	            R apply(T t)
    //Comparator	    (T, T) -> int	    int compare(T o1, T o2)
    //Runnable	        () -> void	        void run()
    //Callable	        () -> T	            V call()


    // Function<T, R>
    // 객체 T를 매개변수로 받아서 처리한 후 R로 반환하는 함수형 인터페이스
    // R apply(T t)를 추상메소드
    // andThen : 첫 번째 함수가 실행된 이후에 다음 함수를 연쇄적으로 실행하도록 연결
    // compose : 첫 번째 함수 실행 이전에 먼저 함수를 실행하여 연쇄적으로 연결해준다는 점에서 차이

    // Consumer<T>
    // 객체 T를 매개변수로 받아서 사용하며, 반환값은 없는 함수형 인터페이스
    // void accept(T t)를 추상메소드
    // andThen : 하나의 함수가 끝난 후 다음 Consumer를 연쇄적으로 이용

    // Supplier <T>
    // 공급자
    // 매개변수 없이 반환값 만을 갖는 함수형 인터페이스
    // T get()을 추상 메소드

    // Predicate<T>
    // (사실이라고) 단정하다
    // 객체 T를 매개 변수로 받아 처리한 후 Boolean을 반환하는 함수형 인터페이스
    // boolean test(T t)을 추상 메서드



}


