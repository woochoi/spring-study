package com.example.reactivestream.example;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedIntPublisher
   implements Flow.Publisher<FixedIntPublisher.Result> {
    @Data
    public static class Result {
        private final Integer value;
        // Subscriber 에게 값을 전달하는 시점에 몇번째 요청을 한건지 저장
        private final Integer requestCount;
    }

    @Override // 새로운 subscriber 가 subscribe 하는 순간..
    public void subscribe(Flow.Subscriber<? super Result> subscriber)
    {
        // Publisher 는 subscribe 를 제공
        // subscribe 안에 여러개의 subscriber 를 등록할 수 있다.
        // subscriber 가 추가되는 시점에 Subscription (리모컨) 을 전달
        var numbers = Collections.synchronizedList(
                new ArrayList<>(List.of(1,2,3,4,5,6,7))
        );
        Iterator<Integer> iterator = numbers.iterator();

        // 리모콘(Subscription 생성)
        var subscription = new IntSubscription(subscriber, iterator); // Subscription 에게 넘겨줌

        // 리모콘(subscription 생성)을 subscriber 의 onSubscribe 을 호출하면서 넘겨준다!
        subscriber.onSubscribe(subscription); // Subscriber 가 onSubscribe 구현되어 있음
    }

    /**
     public static interface Subscriber<T> {
         // Subscribe 하는 시점에 Publisher 로 부터
         // Subscription 을 받을 수 있는 인자 제공
         public void onSubscribe(Flow.Subscription subscription);
         public void onNext(T item);
         public void onError(Throwable throwable);
         public void onComplete();
     }
     */

    @RequiredArgsConstructor
    private static class IntSubscription implements Flow.Subscription {
        private final Flow.Subscriber<? super Result> subscriber; // subscriber
        private final Iterator<Integer> numbers; // iterator 1,2,3,4,5,6,7

        /**
        private IntSubscription(
                Flow.Subscriber<? super Result> subscriber,
                Iterator<Integer> numbers)
            {
                    this.subscriber = subscriber;
                    this.numbers = numbers;
            }
         */

        private final ExecutorService executor = Executors.newSingleThreadExecutor();
        private final AtomicInteger count = new AtomicInteger(1);
        private final AtomicBoolean isCompleted = new AtomicBoolean(false);

        @Override
        public void request(long n) {
            // 아래는 일부 Publisher 가 하는일을 대체
            executor.submit(() -> { // subscriber 의 onNext 와 subscription 의 request가 동기적으로 되면 안되기 때문
                for (int i = 0; i < n; i++) { // 최초(1) or 3
                    if (numbers.hasNext()) { // iterator
                        int number = numbers.next();
                        numbers.remove();
                        subscriber.onNext(new Result(number, count.get())); // subscriber 에게 전달
                    } else {
                        var isChanged = isCompleted.compareAndSet(false, true);
                        if (isChanged) { // 더 이상 값이 없으면
                            executor.shutdown();
                            subscriber.onComplete();
                            isCompleted.set(true);
                        }
                        break;
                    }
                }
                count.incrementAndGet(); // 요청 횟수를 저장 후 전달
            });
        }

        @Override
        public void cancel() {
            subscriber.onComplete();
        }
    }

}
