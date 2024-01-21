package com.example.java8.generic;

public class Test {

    /*
    - public class 클래스명<T>
    - public interface 인터페이스명<E>
    - 제네릭스의 범위 지정 <T extends Number>
    */

    // 클래스명
    public class SampleClass<T> {
        private T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    // 인터페이스명
    public interface InterfaceSample<T> {
        T testMethod(T t);
    }

    public static void main(String[] args) {



    }



}

