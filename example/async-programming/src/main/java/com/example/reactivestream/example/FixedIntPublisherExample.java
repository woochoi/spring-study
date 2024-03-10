package com.example.reactivestream.example;

import lombok.SneakyThrows;

import java.util.concurrent.Flow;

public class FixedIntPublisherExample {
    @SneakyThrows
    public static void main(String[] args) {
        // Cold Publisher
        // subscribe 가 시작되는 순간부터 데이터를 생성하고 전송
        // 파일 읽기, 웹 API 요청
        Flow.Publisher publisher = new FixedIntPublisher(); // 최초 연결시 한개 만큼을 요청 : 1
        //Flow.Subscriber subscriber = new RequestNSubscriber<>(Integer.MAX_VALUE);
        Flow.Subscriber subscriber = new RequestNSubscriber<>(3);

        publisher.subscribe(subscriber); // 새로운 subscriber 가 subscribe 하는 순간..


        Thread.sleep(100);

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


        // Cold Publisher
        // subscriber 가 없더라도 데이터를 생성하고 stream 에 push 하는 publisher
        // 실시간성...
    }
}
