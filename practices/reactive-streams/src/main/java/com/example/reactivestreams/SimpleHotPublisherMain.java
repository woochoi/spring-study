package com.example.reactivestreams;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleHotPublisherMain {
    @SneakyThrows
    public static void main(String[] args) {
        // prepare publisher
        var publisher = new SimpleHotPublisher();

        //Thread.sleep(1000);
        //publisher.shutdown();

        // prepare subscriber1
        var subscriber = new SimpleNamedSubscriber<>("subscriber1");
        publisher.subscribe(subscriber);

        // cancel after 5s
        Thread.sleep(5000);
        subscriber.cancel();

        // 간격없이 Thread.sleep 에서 풀리자 마자 바로 subscribe 한다!

        // prepare subscriber2,3 > // 2와 3이 동시에 가져간다! --> 50부터 시작한다!
        var subscriber2 = new SimpleNamedSubscriber<>("subscriber2"); // 동산
        var subscriber3 = new SimpleNamedSubscriber<>("subscriber3"); // 동시
        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        // cancel after 5s
        Thread.sleep(5000);
        subscriber2.cancel();
        subscriber3.cancel();


        // 위와 같이 간격없이 Thread.sleep 에서 풀리자 마자 바로 subscribe 하지 않고 1초를 대기하고 나서 진행한다!
        Thread.sleep(1000); // 1초 동안은 publisher 가 계속 생산을 하는데, 즉 item 을 numbers 에 추가하는데 아무도 구독하지 않고 있다가...

        /** 간격이 10개 ---> 100 에 히나씩 찍었으니...
         23:10:40.997 [pool-4-thread-1] INFO com.example.reactivestreams.SimpleNamedSubscriber -- name: subscriber3, onNext: 97
         23:10:40.997 [pool-3-thread-1] INFO com.example.reactivestreams.SimpleNamedSubscriber -- name: subscriber2, onNext: 97
         23:10:41.097 [pool-3-thread-1] INFO com.example.reactivestreams.SimpleNamedSubscriber -- name: subscriber2, onNext: 98
         23:10:41.098 [pool-4-thread-1] INFO com.example.reactivestreams.SimpleNamedSubscriber -- name: subscriber3, onNext: 98
         23:10:41.170 [main] INFO com.example.reactivestreams.SimpleNamedSubscriber -- cancel
         23:10:41.171 [main] INFO com.example.reactivestreams.SimpleNamedSubscriber -- onComplete
         23:10:41.171 [main] INFO com.example.reactivestreams.SimpleNamedSubscriber -- cancel
         23:10:41.171 [main] INFO com.example.reactivestreams.SimpleNamedSubscriber -- onComplete
         23:10:42.172 [main] INFO com.example.reactivestreams.SimpleNamedSubscriber -- onSubscribe
         23:10:42.172 [pool-5-thread-1] INFO com.example.reactivestreams.SimpleNamedSubscriber -- name: subscriber4, onNext: 108
         23:10:42.220 [pool-5-thread-1] INFO com.example.reactivestreams.SimpleNamedSubscriber -- name: subscriber4, onNext: 109
         23:10:42.322 [pool-5-thread-1] INFO com.example.reactivestreams.SimpleNamedSubscriber -- name: subscriber4, onNext: 110
         */

        // 구독을 시작한다!
        var subscriber4 = new SimpleNamedSubscriber<>("subscriber4");
        publisher.subscribe(subscriber4);


        // cancel after 5s
        Thread.sleep(5000);
        subscriber4.cancel();

        // shutdown publisher
        publisher.shutdown();

    }
}
