package com.example.reactivestreamsimpl.reactor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


@Slf4j
// @RequiredArgsConstructor
public class SimpleSubscriber<T> implements Subscriber<T> {
    private final Integer count; // n개 만큼의 갯수를 받고

    public SimpleSubscriber(Integer count) {
        this.count = count;
    }

    @Override
    public void onSubscribe(Subscription subscription) { // onSubscribe 할때 마다
        log.info("onSubscribe");
        // 위 count 만큼 request 를 한다!
        subscription.request(count);
        log.info("request: {}", count);
    }

    @SneakyThrows
    @Override
    public void onNext(Object o) {
        log.info("onNext, item: {}", o);
        Thread.sleep(100);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("error: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }
}
