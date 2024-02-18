package com.example.completablefuture.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureRunAsyncExample {

    //runAsync
    //반환값이 없는 경우
    //비동기로 작업 실행 콜

    public static void main(String[] arg) throws InterruptedException, ExecutionException {
        log.info("start main");
        var future = CompletableFuture.runAsync(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            log.info("runAsync");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        assert !future.isDone();

        Thread.sleep(1000);
        assert future.isDone();
        assert future.get() == null;

        System.out.println("Thread: " + Thread.currentThread().getName());
        log.info("end main");
    }
}
