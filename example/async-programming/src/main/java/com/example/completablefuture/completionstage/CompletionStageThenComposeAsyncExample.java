package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;


/**
 * Future 에서 또 다른 Future 를 호출한다...
 * Future 와 Future 를 연계 반환
 * Future 안에 있는 값을 까서 다음에 넘겨야 되는 상황
 */

@Slf4j
public class CompletionStageThenComposeAsyncExample {
    public static void main(String[] args)
            throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage(); //  CompletionStage<Integer> return 1;
        stage.thenComposeAsync(value -> {
            var next = Helper.addOne(value);
            log.info("in thenComposeAsync: {}", next);
            return next; // CompletableFuture
        }).thenComposeAsync(value -> {
            var next = Helper.addResultPrefix(value);
            log.info("in thenComposeAsync2: {}", next);
            return next; // CompletableFuture
        }).thenAcceptAsync(value -> {
            log.info("{} in thenAcceptAsync", value);
        });

        Thread.sleep(1000);
        log.info("end main");
    }
}
