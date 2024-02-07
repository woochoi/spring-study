package com.example.completablefuture.completablefuture;

import com.example.completablefuture.completionstage.Helper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class CompletableFutureAllOfExample {
    public static void main(String[] args) throws InterruptedException {

        // allOf
        // 여러개의 CompletableFuture 를 모아서 하나의 CompletableFuture 로 변환
        // 모든 CompletableFuture 가 완료되면 상태가 done
        // Void 를 반환하므로 일일히 각각의 값에 get 으로 접근해야 한다!

        var startTime = System.currentTimeMillis();
        var firstFuture = Helper.waitAndReturn(100, 1);
        var secondFuture = Helper.waitAndReturn(500, 2);
        var thirdFuture = Helper.waitAndReturn(1000, 3); // 가장 오래걸리는 1012ms

        CompletableFuture.allOf(firstFuture, secondFuture, thirdFuture)
                .thenAcceptAsync(v -> { // Void 반환하므로 v 는 null 값!
                    log.info("after allOf"); // ForkJoinPool.commonPool-worker-3
                    try {
                        // ForkJoinPool.commonPool-worker-3
                        log.info("first: {}", firstFuture.get());
                        log.info("second: {}", secondFuture.get());
                        log.info("third: {}", thirdFuture.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).join();

        var endTime = System.currentTimeMillis();
        log.info("elapsed: {}ms", endTime - startTime); // 가장 오래걸리는 elapsed: 1012ms
    }

    // CompletableFuture 의 한계
    // 지연 로딩 기능을 제공하지 않는다!
    // --> CompletableFuture 를 반환하는 함수를 호출시 즉시 작업이 실행 된다!
    // 지속적으로 생성되는 데이터 처리가 힘들다!
    // --> CompletableFuture 에서 데이터를 반환하고 나면 다시 다른 값을 전달하기 어렵다!
}
