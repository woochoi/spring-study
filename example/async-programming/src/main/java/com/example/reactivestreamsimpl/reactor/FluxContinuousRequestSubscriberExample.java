package com.example.reactivestreamsimpl.reactor;

import reactor.core.publisher.Flux;

import java.util.List;

public class FluxContinuousRequestSubscriberExample {
    public static void main(String[] args) {
        getItems().subscribe(new ContinuousRequestSubscriber<>());
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
