package com.example.webflux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class FluxExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.from(getItems())
                .subscribe();
        log.info("end main");


        Flux<Integer> intFlux = Flux.just(1,2,3);
        intFlux.map(i -> i * 2).subscribe(System.out::println);

        //Flux<Integer> intFluxRange = Flux.range(1,10);
        //Flux<String> stringFlux = Flux.fromIterable(List.of("foo", "bar"));
        //Flux<String> anotherStringFlux = Flux.fromArray(new String[]{"foo", "bar"});


    }

    private static Flux<Integer> getItems() {
        return Flux.fromIterable(List.of(1, 2, 3, 4, 5));
    }
}