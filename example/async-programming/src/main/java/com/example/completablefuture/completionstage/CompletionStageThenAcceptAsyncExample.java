package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@Slf4j
public class CompletionStageThenAcceptAsyncExample {
    public static void main(String[] args)
            throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.finishedStage();
        stage.thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i); // 별도의 쓰레드에서 실행
        }).thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync2", i);
        });
        log.info("after thenAccept");
        Thread.sleep(100);
    }
}

/**
 * 36:12 [main] - start main
 * 36:12 [ForkJoinPool.commonPool-worker-1] - return in future
 * 36:12 [main] - after thenAccept
 * 36:12 [ForkJoinPool.commonPool-worker-1] - 1 in thenAcceptAsync
 * 36:12 [ForkJoinPool.commonPool-worker-2] - null in thenAcceptAsync2
 */
