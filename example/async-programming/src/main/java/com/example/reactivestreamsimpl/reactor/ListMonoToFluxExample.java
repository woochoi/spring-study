package com.example.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class ListMonoToFluxExample {
    public static void main(String[] args) {
        log.info("start main");

        // Mono 의 값으로 여러개의 값을 전달하는 Flux 를 만들고 연결
        getItems() // Mono 의 결과를
                // ThenCompose
                .flatMapMany(value -> Flux.fromIterable(value)) // == Flux::fromIterable
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));

        log.info("end main");
    }

    private static Mono<List<Integer>> getItems() {
        return Mono.just(List.of(1, 2, 3, 4, 5));
    }
}
