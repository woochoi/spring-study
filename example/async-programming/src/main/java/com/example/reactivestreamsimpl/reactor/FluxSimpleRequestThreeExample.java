package com.example.reactivestreamsimpl.reactor;

import com.example.reactivestreamsimpl.reactor.SimpleSubscriber;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class FluxSimpleRequestThreeExample {
    public static void main(String[] args) {
        getItems().subscribe(new SimpleSubscriber<>(3)); // 한번만
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
