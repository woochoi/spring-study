package com.example.completablefuture;

import java.util.concurrent.Future;

public class FutureCancelExample {

    public static void main(String[] args) {
        Future<Integer> future = FutureHelper.getFuture();
        var successToCancel = future.cancel(true);
        //System.out.println(future.isCancelled());
        //System.out.println(future.isDone());
        //System.out.println(successToCancel); // true
        assert !future.isCancelled();
        assert !future.isDone();
        assert successToCancel;

        successToCancel = future.cancel(true);
        //System.out.println(future.isCancelled());
        //System.out.println(future.isDone());
        //System.out.println(successToCancel); // false
        assert future.isCancelled();
        assert future.isDone();
        assert successToCancel;
    }
}
