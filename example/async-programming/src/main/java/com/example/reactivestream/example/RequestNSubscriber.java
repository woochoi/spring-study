package com.example.reactivestream.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

@Slf4j
@RequiredArgsConstructor
public class RequestNSubscriber<T> implements Flow.Subscriber<T>{
    private final Integer n; // 3
    /**
     * public RequestNSubscriber(Integer n) {
     *   this.n = n;
     * }
     */

    /**
     * public static interface Subscription {
     *    public void request(long n);
     *    public void cancel();
     * }
     */
    private Flow.Subscription subscription; // request(long n); cancel()
    private int count = 0;

    @Override
    public void onSubscribe(Flow.Subscription subscription) { // Publisher 에서 subscription 을 받는 순간
        this.subscription = subscription; // 저장
        this.subscription.request(1); // 최초 연결시 한개 만큼을 요청
    }

    @Override
    public void onNext(T item) {
        log.info("item: {}", item); // Result
        log.info("count: {}, n: {}", count, n);

        if (count++ % n == 0) { // count++ % 3 == 0
            log.info("send request");
            this.subscription.request(n); // 3
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("complete");
    }
}
