package com.example.completablefuture.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureCompleteExample {
    // 외부에서도 CompletableFuture 의 상태값을 바꿀수 있게한다!
    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        assert !future.isDone();

        var triggered = future.complete(1);
        assert future.isDone();
        assert triggered;
        assert future.get() == 1;

        // CompletableFuture 가 완료되지 않았다면 주어진 값으로 채운다!
        // complete 로 상태가 바뀌었다면 true

        triggered = future.complete(2);
        assert future.isDone(); // true
        assert !triggered; // triggered = false
        assert future.get() == 1;

    }
}
