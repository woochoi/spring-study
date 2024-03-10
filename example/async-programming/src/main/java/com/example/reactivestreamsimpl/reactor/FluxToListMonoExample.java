package com.example.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class FluxToListMonoExample {
    public static void main(String[] args) {

        // 아이템 1, 2, 3, 4, 5 를 onNext 로 모아서
        // [1, 2, 3, 4, 5] 리스트를 통채로 받는 Mono
        // Flux 에 collectList 로 모으게 되면
        // collectList 는 일종의 Subscriber 겸 Publisher 역할을 한다
        // onNext 로 아이템들을 쭉쭉 받아서 내부 배열에 저장한다
        // onComplete 가 내려오는 순간 가지고 있던 값을 onNext 로 다음으로 전달하는 역할
        // 아래로는 Mono 로 전달된다!

        getItems()
                .collectList() // Flux 애 collectList 를 하게되면 Mono 를 반환하게 된다!
                .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));

        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}
