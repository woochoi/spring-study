package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

/**
 * 이전 Task로 부터 값을 받지 않고 값을 반환하지 않는다.
 * 다음 Task 에게 null 이 전달 된다!
 * future가 완료되었다는 이벤트를 기록할때 유용하다!
 */


@Slf4j
public class CompletionStageThenRunAsyncExample {
    public static void main(String[] args)
            throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenRunAsync(() -> {
            log.info("in thenRunAsync");
        }).thenRunAsync(() -> {
            log.info("in thenRunAsync2");
        }).thenAcceptAsync(value -> {
            log.info("{} in thenAcceptAsync", value);
        });

        Thread.sleep(100);
    }
}
