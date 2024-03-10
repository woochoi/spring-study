package com.example.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxNoSubscribeExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems(); // subscribe X
        log.info("end main");
    }

    // 별도의 subscribe 를 하지않으면 Publisher 는 Publish 를 하지 않음

    private static Flux<Integer> getItems() {
     return Flux.create(integerFluxSink -> { // 5개를 넘겨주는 Flux 를 만듬
         log.info("start getItems");
         for (int i = 0; i < 5; i++) {
             integerFluxSink.next(i);
         }
         integerFluxSink.complete();
         log.info("end getItems");
     });
    }
}
