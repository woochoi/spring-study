package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class CompletionStageThenAcceptAsyncExecutorExample {
    public static void main(String[] args)
            throws InterruptedException {

        var single = Executors.newSingleThreadExecutor();
        var fixed = Executors.newFixedThreadPool(10);

        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage(); // 종료
        // 바로 종료
        stage.thenAcceptAsync(i -> { // 별도 실행
            log.info("{} in thenAcceptAsync", i);
        }, fixed).thenAcceptAsync(i -> { // 별도 실행
            log.info("{} in thenAcceptAsync2", i);
        }, single);

        log.info("after thenAccept");
        Thread.sleep(200);

        single.shutdown();
        fixed.shutdown();

        // ForkJoinPool 외 다른 쓰레드에서 실행 시키는 것도 가능하다!
    }
}
