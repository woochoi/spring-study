package com.example.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;

@Slf4j
public class ForkJoinPoolExample {
    public static void main(String[] args)
            throws InterruptedException {

        var parallelism = ForkJoinPool.commonPool()
                .getParallelism();
        var availableCpuCore = Runtime.getRuntime()
                .availableProcessors();

        log.info("commonPool parallelism: {}", parallelism);
        // commonPool parallelism: 11 ==> 12 - 1

        log.info("available cpu core: {}", availableCpuCore);
        // available cpu core: 12
    }
}
