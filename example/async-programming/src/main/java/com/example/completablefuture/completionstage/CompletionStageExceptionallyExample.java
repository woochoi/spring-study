package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletionStageExceptionallyExample {
    public static void main(String[] args)
            throws InterruptedException {
        Helper.completionStage()
                .thenApplyAsync(i -> {
                    log.info("in thenApplyAsync");
                    return i / 0; // 오류!!!
                }).exceptionally(e -> {
                    log.info("{} in exceptionally", e.getMessage());
                    return 0;
                }).thenAcceptAsync(value -> {
                    log.info("{} in thenAcceptAsync", value);
                }); // 오류를 정상으로 핸들링 가능

        Thread.sleep(1000);
    }
}
