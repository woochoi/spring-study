package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletionStageExample {

    // 총 코어 개수: 12(8 성능 및 4 효율)
    // 메모리: 16 GB
    public static void main(String[] arg) throws InterruptedException {
        Helper.completionStage()
                .thenApplyAsync(value -> {
                    log.info("[2] thenApplyAsync: {}", value);
                    return value + 1;
                })
                .thenAcceptAsync(value -> {
                    log.info("[3] thenAccept: {}", value);
                })
                .thenRunAsync(() -> { // 값을 반환하지 않는다. 다음 task에 null이 전달된다!
                    log.info("[4] thenRun");
                })
                .thenAcceptAsync(value -> {
                    log.info("[5] thenAccept: {}", value);
                })
                .exceptionally(e -> {
                    log.info("[6] exceptionally: {}", e.getMessage());
                    return null;
                });

        Thread.sleep(100);
    }
}
