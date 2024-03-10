package com.example.reactivestreams;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

// HotPublisher 는 새로 subscribe 하는 순간 offset 을 남기고 offset/requiredOffset 기반으로 값을 전달해주는 subscription
@Slf4j
public class SimpleHotPublisher implements Flow.Publisher<Integer> {
    private final ExecutorService publisherExecutor = Executors.newSingleThreadExecutor();
    private final Future<Void> task;
    private List<Integer> numbers = new ArrayList<>();
    private List<SimpleHotSubscription> subscriptions = new ArrayList<>();

    public SimpleHotPublisher() {
        numbers.add(1);

        // 실시간 계속해서 생성 되는 코드 (데이터 생성)
        task = publisherExecutor.submit(() -> {
            for(int i = 2; !Thread.interrupted(); i++) { // 계속 추가...

                // 새로운 값이 추가되었을때, Subscriber 들에게 전파를 해야 된다!
                // 혹시 전에 Subscriber 가 Publisher 너에게 요청한게 있나?
                // 요청한 값이 남아 있으면 (요청 한 값을 줄 수(전달 할 수) 있는 상황이면 바로 줘라!
                // 또는 Subscriber 가 따로 request 하지 않았으면 그냥 알고만 있어...
                // onSubscribe 할때마다 SimpleHotSubscription 을 저장하는 subscriptions 를 만든다!
                numbers.add(i);

                //log.info("numbers: {}", numbers);

                // 추가된 subscriptions 를 쭉 순회하면서, 값이 추가 될때 마다 브로드캐스팅을 한다!
                subscriptions.forEach(SimpleHotSubscription::wakeup); // 메소드 레퍼런스 wakeup
                // wakeup --> 이제 값이 생성되고 Subscriber 에게 값을 전닳해 줄 수 있는 상황이면 전달해줘!

                Thread.sleep(100);
            }

            return null;
        }); //--> SimpleHotPublisher 가 만들어진 순간부터 계속 데이터를 생성하는 코드...

    }

    public void shutdown() {
        this.task.cancel(true);
        publisherExecutor.shutdown();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        // Subscriber 에게 줄 subscription 을 민들고 추가 한다!
        var subscription = new SimpleHotSubscription(subscriber);
        subscriber.onSubscribe(subscription);
        subscriptions.add(subscription);
    }

    private class SimpleHotSubscription implements Flow.Subscription {
        // Subscription 이 최초 생성 됬을때,
        // 생성된 시점의 numbers,
        // 즉, Publisher 가 계속해서 쌓고 있는 위 numbers 에서 가장 마지막 값을 가리키게 해야된다!
        // numbers 계속 쌓이고 있는 값들 1,2,3,4,5, ... 중에서 subscribe 하는 순간 부터 데이터를 받아간다.
        // Subscriber 들은 각각 offset 이 따로 있어야 된다!

        private int offset; // 어디까지 전달 했는지 가리킨다
        private int requiredOffset; // 줄 수 없을때, 미리 요청정보를 쌓아 놓는다.
        private final Flow.Subscriber<? super Integer> subscriber; // 어떠한 Subscriber 를 가지고 있는지

        // 요청을 별도의 쓰레드에서 돌리기 위한 ExecutorService 를 추가
        private final ExecutorService subscriptionExecutorService = Executors.newSingleThreadExecutor();

        public SimpleHotSubscription(Flow.Subscriber<? super Integer> subscriber) { // Flow.Subscriber<? super Integer> subscriber
            int lastElementIndex = numbers.size() - 1; // 위 numbers 의 미지막 값
            this.offset = lastElementIndex;
            this.requiredOffset = lastElementIndex;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) { // n 개를 요청한다.
            requiredOffset += n;

            onNextWhilePossible();
        }

        public void wakeup() {
            // 이제 값이 생성되고 Subscriber 에게 값을 전닳해 줄 수 있는 상황이면 전달해줘!
            onNextWhilePossible();
        }


        @Override
        public void cancel() {
            this.subscriber.onComplete();
            if (subscriptions.contains(this)) {
                subscriptions.remove(this);
            }
            subscriptionExecutorService.shutdown();
        }

        private void onNextWhilePossible() {
            subscriptionExecutorService.submit(() -> {
                // offset 은 계속 증가 해서 requiredOffset 까지 도달해야 된다!
                // 하지만 offset 이 numbers 보다 커지면 안됨
                while (offset < requiredOffset && offset < numbers.size()) {
                    var item = numbers.get(offset); // 전달 값(offset = 위치)
                    subscriber.onNext(item); // 값을 전달
                    offset++;
                }
            });
        }

    }

}
