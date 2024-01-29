package com.example.webflux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoExample {
    public static void main(String[] args) {
        log.info("start Mono");
        //System.out.println("Mono Test");

        /**
         * 아래 리액티브 코드는 단계별로 실행되는 것처럼 보이겠지만, 실제로는 데이터가 전달되는 파이프라인을 구성하는 것입니다.
         * 그리고 파이프라인의 각 단계에서는 어떻게 하든 데이터가 변경됩니다.
         * 또한, 각 오퍼레이션은 같은 스레드로 실행되거나 다른 스레드로 실행될 수 있습니다.
         */
        Mono.just("Craig")
                .map(s -> s.toUpperCase())
                .map(s -> "Hello, " + s + "!")
                .subscribe(System.out::println);
        /**
         * 세 개의 Mono가 생성됩니다
         * just 메서드가 첫 번째 Mono를 생성합니다.
         * 그리고 그 값을 map 메서드가 받아서 대문자로 변경한 뒤 새로운 두번째 Mono를 생성합니다.
         * 이것이 다음 map 메서드에 전달되어 문자열 결합 후 새로운 세번째 Mono를 생성합니다.
         * 그리고 끝으로 subscribe 메서드에서는 세번째 Mono를 구독하여 데이터를 수신하고 출력합니다.
         */



    }
}

