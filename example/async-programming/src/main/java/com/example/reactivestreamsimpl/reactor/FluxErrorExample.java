package com.example.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxErrorExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems().subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.create(integerFluxSink -> {
            integerFluxSink.next(0);
            integerFluxSink.next(1);
            var error = new RuntimeException("error in flux");
            integerFluxSink.error(error);
        });
    }
}
