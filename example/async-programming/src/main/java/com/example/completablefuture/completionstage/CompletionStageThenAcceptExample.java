package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@Slf4j
public class CompletionStageThenAcceptExample {
    public static void main(String[] args) throws InterruptedException {

        log.info("start main");
        CompletionStage<Integer> stage = Helper.finishedStage();
        stage.thenAccept(i -> {
            log.info("{} in thenAccept", i);    // 1
        }).thenAccept(i -> { // thenAccept 반환값이 없다. 즉, 다음으로 넘기는 값이 없다!
            log.info("{} in thenAccept2", i);   // null
        });
        log.info("after thenAccept");
        Thread.sleep(100); // 데몬 쓰레드
    }
}

/**
 * 35:31 [main] - start main
 * 35:31 [ForkJoinPool.commonPool-worker-1] - return in future
 * 35:31 [main] - 1 in thenAccept
 * 35:31 [main] - null in thenAccept2
 * 35:31 [main] - after thenAccept
 */