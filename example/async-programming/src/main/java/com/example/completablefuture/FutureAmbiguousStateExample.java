package com.example.completablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureAmbiguousStateExample {
    public static void main(String[] args)
            throws InterruptedException, ExecutionException {
        Future<Integer> futureToCancel = FutureHelper.getFuture();

        futureToCancel.cancel(true);
        assert futureToCancel.isDone(); // true

        // 오류로 종료됨!
        Future<Integer> futureWithException = FutureHelper.getFutureWithException();
        Exception exception = null;
        try {
            futureWithException.get();
        } catch (ExecutionException e) {
            exception = e;
        }
        assert futureWithException.isDone(); // true
        assert exception != null;
    }

    // 완료되거나 에러가 발생했는지 구분하기 어렵다!

}