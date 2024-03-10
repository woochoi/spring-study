package com.example.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class MonoToFluxExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().flux() // Mono 를 Next 로 한번 호출하고 onComplete, 하나의 아이템을 전달하는 Flux
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));

        // == 같다
        //Flux.from(getItems())
        //       .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));


        log.info("end main");
    }

    private static Mono<List<Integer>> getItems() {
        return Mono.just(List.of(1,2,3,4,5));
    }
}
