package com.example.reactivestreams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// ColdPublisher 는 새로 subscribe 할때마다 매번 값을 새로 만들어서 전달해주는 subscription
@Slf4j
public class SimpleColdPublisher implements Flow.Publisher<Integer> {
    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {

        var iterator = Collections.synchronizedList( // thread-safe
                IntStream.range(1, 10).boxed().collect(Collectors.toList()) // toList()
        ).iterator();

        // 리모콘
        var subscription = new SimpleColdSubscription(iterator, subscriber);

        // 이 리모콘을 subscriber 에 전달
        subscriber.onSubscribe(subscription); // SimpleNamedSubscriber
    }

    @RequiredArgsConstructor
    public class SimpleColdSubscription implements Flow.Subscription { // subscription
        private final Iterator<Integer> iterator;
        private final Flow.Subscriber<? super Integer> subscriber;
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        @Override
        public void request(long n) {
            executorService.submit(() -> {
                for (int i = 0; i < n; i++) { // 요청 받은 만큼
                    if (iterator.hasNext()) {
                        var number = iterator.next();
                        iterator.remove();
                        subscriber.onNext(number); // 값을 넘기는
                    } else {
                        subscriber.onComplete();
                        executorService.shutdown();
                        break;
                    }
                }
            });
        }

        @Override
        public void cancel() {
            subscriber.onComplete();
        }
    }
}