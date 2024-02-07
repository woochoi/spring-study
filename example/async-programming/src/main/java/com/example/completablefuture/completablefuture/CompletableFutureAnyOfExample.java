package com.example.completablefuture.completablefuture;

import com.example.completablefuture.completionstage.Helper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class CompletableFutureAnyOfExample {
    public static void main(String[] args)
            throws InterruptedException {

        // anyOf
        // Void 가 아니라 Object 를 반환!
        // 주어진 CompletableFuture 중에사 하나라도 완료되면 상태가 done
        // 제일 먼저 done 상태가 되는 CompletableFuture 의 값을 반환

        var startTime = System.currentTimeMillis();
        var firstFuture = Helper.waitAndReturn(100, 1); // 가장 빨리 실행하는...
        var secondFuture = Helper.waitAndReturn(500, 2);
        var thirdFuture = Helper.waitAndReturn(1000, 3);

        CompletableFuture.anyOf(firstFuture, secondFuture, thirdFuture)
                .thenAcceptAsync(v -> {
                    log.info("after anyOf");
                    log.info("가장먼저오는 값은(first value): {}", v);
                }).join();

        var endTime = System.currentTimeMillis();
        log.info("elapsed: {}ms", endTime - startTime);
    }

    // CompletableFuture 의 한계
    // 지연 로딩 기능을 제공하지 않는다!
        // --> CompletableFuture 를 반환하는 함수를 호출시 즉시 작업이 실행 된다!
    // 지속적으로 생성되는 데이터 처리가 힘들다!
        // --> CompletableFuture 에서 데이터를 반환하고 나면 다시 다른 값을 전달하기 어렵다!

}
