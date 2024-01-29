### 서적 : https://github.com/spring-boot-in-practice/repo
### 강의 : https://github.com/gaaon/webflux-examples

![img.png](img.png)

```java
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> s);
}

public interface Subscriber<T> {
    void onSubscribe(Subscription s);
    void onNext(T t);
    void onError(Throwable t);
    void onComplete();
}

public interface Subscription {
    void request(long n);
    void cancel();
}
```

Mono : 하나의 데이터 항목만 갖는 타입 
</br>
Flux : 0, 1 또는 다수의 데이터를 갖는 타입




### 참고
- https://velog.io/@backtony/Spring-Reactor-%EA%B0%9C%EC%9A%94
- https://velog.io/@rnqhstlr2297/Spring-Webflux%EC%99%80-WebClient
- https://velog.io/@backtony/Spring-WebFlux-%EB%A6%AC%EC%95%A1%ED%84%B0-%ED%83%80%EC%9E%85-%EB%A6%AC%ED%8F%AC%EC%A7%80%ED%86%A0%EB%A6%AC-%ED%85%8C%EC%8A%A4%ED%8A%B8
- https://alwayspr.tistory.com/44
- 
- https://velog.io/@holicme7/Apache-Kafka-Kafka-Connect-%EB%9E%80



