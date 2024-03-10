package com.example.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class FluxToMonoExample {
    public static void main(String[] args) {
        // Flux -> Mono
        log.info("start main");

        Mono.from(getItems()) // 첫번째 값만 전달..,
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));

        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
