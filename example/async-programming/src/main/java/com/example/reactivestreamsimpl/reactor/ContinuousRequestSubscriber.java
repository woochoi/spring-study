package com.example.reactivestreamsimpl.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class ContinuousRequestSubscriber<T> implements Subscriber<T> {
    private final Integer count = 1;
    private Subscription subscription; // 넘어온 subscription 를 저장


    // Flux 를 통해서 1, 2, 3, 4, 5 흘려준다!
    @Override
    public void onSubscribe(Subscription subscription) { // 최초 Subscribe 시 1개 만큼 요청
        this.subscription = subscription;
        log.info("subscribe");
        subscription.request(count); // 값을 가지고 있는 갯수만큼 요청한다! 최초 현재 1 만큼 요청
        log.info("request: {}", count);
    }

    @SneakyThrows
    @Override
    public void onNext(T t) {
        log.info("item: {}", t); // 하나를 처리하고 나면, 로그를 찍고 나면

        Thread.sleep(1000); // 1초룰 기다리고
        subscription.request(1); // 다음을 요청 한다 --> 본인이 처리 가능한 만큼만 요청 (backPressure)
        // 그리고 다음 subscription 은 다시 onNext 를 통해서 값을 전달해 준다!

        // 아무리 Subscriber 에서 감당하기 힘든게 오지않고
        // publisher 애서는 많은 데이터를 가지고 있어도 Subscriber 가 필요한 만큼만 처리

        log.info("request: {}", count);
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
