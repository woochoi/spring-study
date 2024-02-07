package com.example.completablefuture.completionstage;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
public class Helper {

    @SneakyThrows
    public static CompletionStage<Integer> finishedStage() {
        var future = CompletableFuture.supplyAsync(() -> { // CompletableFuture 를 만들어주는 역할
            log.info("return in future");
            return 1;
        }); // CompletableFuture 종료 후 반환

        Thread.sleep(100);
        return future;
        // 상태가 done 보장
    }

    public static CompletionStage<Integer> runningStage() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000); // 진행 중인 CompletableFuture
                log.info("I'm running!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return 1;
        });
    }

    // CompletableFuture<T> implements Future<T>, CompletionStage<T> {
    public static CompletionStage<Integer> completionStage() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("[1] return in future");
            return 1;
            // 종료
        });
    }

    public static CompletionStage<Integer> completionStageAfter1s() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getCompletionStage");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });
    }

    public static CompletionStage<Integer> addOne(int value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return value + 1;
        });
    }

    public static CompletionStage<String> addResultPrefix(int value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "result: " + value;
        });
    }


    // millis 만큼 기다렸다가 value 를 반환!
    public static CompletableFuture<Integer> waitAndReturn(int millis, int value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("waitAndReturn: {}ms", millis);
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return value;
        });
    }
}
