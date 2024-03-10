package com.example.reactivestreamsimpl.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class FluxSimpleExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        getItems().subscribe( // 모두 [main] 쓰레드서 실행
                new SimpleSubscriber<>(Integer.MAX_VALUE) // 생성자
        );

        // 다른 쓰레드에서 onNext 를 실행하고 싶다.
        // 즉 Subscriber 를 다른 쓰레드에서 실행하고 싶다. (SubscribeOn)
        // --> FluxSimpleSubscribeOnExample

        log.info("end main");

        Thread.sleep(1000);

        /**
         * Flux는 0에서 N개의 아이템을 전달한다. 그 중 하나만을 선택해서 Mono로 전달하려면 .next() 메서드를 사용해서 전달한다.
         * next()는 다음 onNext 시그널에 전달되는 아이템을 단일 Mono로 전달한다.
         */
        //Flux.range(1, 10)
        //        .next()
        //        .subscribe(System.out::println);

    }

    private static Flux<Integer> getItems() { // Flux 를 사용해서 간단하게 Publisher 를 만듬
        // return Flux.from((Publisher<? extends Integer>) List.of(1,2,3,4,5));
        // 고정된 갯수 (1,2,3,4,5) 를 Subscriber 에게 전달
        // 파라미터로 입력된 Iterable에 포함된 아이템들을 새로운 Flux 인자로 방출 emit
        return Flux.fromIterable(List.of(1,2,3,4,5));
    }
}
