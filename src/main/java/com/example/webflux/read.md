


public interface Publisher<T> {
    void subscribe(Subscriber<? super T> var1);
}


public interface Subscriber<T> {
void onSubscribe(Subscription var1);
    void onNext(T var1);
    void onError(Throwable var1);
    void onComplete();
}

public interface Subscription {
    void request(long var1);
    void cancel();
}
