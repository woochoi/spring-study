package com.example.completablefuture.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class CompletableFutureIsCompletedExceptionallyExample {
    public static void main(String[] args)
            throws InterruptedException {
        var futureWithException = CompletableFuture.supplyAsync(() -> {
            return 1 / 0;
        });
        Thread.sleep(100);
        assert futureWithException.isDone();    // true
        assert futureWithException.isCompletedExceptionally(); // true
    }
}