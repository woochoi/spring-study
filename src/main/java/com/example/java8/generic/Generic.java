package com.example.java8.generic;


import lombok.extern.slf4j.Slf4j;



@Slf4j
public class generic {
    public static void main(String[] args) {

        /**
        public <T extends Comparable<? super T>> T max(Collection<? extends T> col){

        }
            T 를 타입 파라미터 (Type parameter)
                --> 타입 파라미터 간에는 상/하위 관계가 없고 raw-type 간에만 상/하위 관계가 존재합니다.
                    ? 등장, 상하위 타입이 개념이 아니라 그냥 임의의 어떤 것
                    예, Number를 상속한 어떤 것! 이라고 하고 싶을 때는 어떻게 할까요?
                    --> <? extends Number>
            ? 를 한정자(Bounded Parameter)


            1. 반환 타입은 T이고 메소드의 인자는 Collection<? extends T>
            2. Collection<? extends T> col은 T 타입보다 하위 타입인 어떤 것을 저장하는 컬렉션을 인자로 받는 메소드인 것을 알 수 있습니다

        */

        //log.info("start main");
        //System.out.println("Test");

        boolean result = GenericMethods.<String, Integer>methodName("key", 3);
        System.out.println(result);
    }

    public static class GenericMethods {
        public static <K, V> boolean methodName(K key, V value) {
            return true;
        }
    }


}


