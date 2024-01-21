package com.example.java8.lambda;


/* 함수형 인터페이스(Functional Interface)는 추상 메서드가 딱 하나만 존재하는 인터페이스 */
@FunctionalInterface
public interface Math {
    int calc(int first, int second);
}
