package com.example.java.thread;

public class SynchronizedSample {
    private int count;
    private static int THREAD_MAX = 300000;

    private static class MyThread implements Runnable {
        private SynchronizedSample _count;

        public MyThread(SynchronizedSample count) {
            this._count = count;
        }

        @Override
        public void run() {
            _count.increment();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SynchronizedSample ss = new SynchronizedSample();
        Thread[] ts = new Thread[THREAD_MAX];
        for (int i = 0; i < THREAD_MAX; i++) {
            ts[i] = new Thread(new MyThread(ss));
            ts[i].start();
        }
        for (int i = 0; i < THREAD_MAX; i++) {
            ts[i].join();
        }
        System.out.println(ss.count);   // 결과: 299944(매번 결과가 다르다.)
    }

    /**
     * 위 코드는 제대로 작동하지 않는다.
     * 위에 this.count++ 연산에서 count 값을 읽은 후 증가하고 재대입되기 전에 다른 스레드가 인터럽트될 수 있기 때문이다.
     * 그러므로 원래 최종 결과 300,000이 되어야 하지만, 299999와 같은 값이 반환 될 수도 있게 된다.
     */
    public void increment() {
        this.count++;
    }

    /**
        이를 방지하려면 increment 메서드를 synchronized 한정자로 한정하면 된다.
        이렇게 함으로써 increment 메소드는 여러 thread로부터 동시에 호출되는 일이 없어진다(나중에 호출된 곳에서는 먼저 들어온 처리가 끝날 때까지 대기한다).
        그래서 결과는 매번 30만이라는 결과를 얻을 수 있다.

        다만, synchronized 한정자가 올바르게 작동하는 것은 메서드가 속한 인스턴스가 동일한 경우이다.
        다른 인스턴스가 있는 경우 인스턴스 수 만큼 동시 실행될 수 있으므로 주의해야 한다.

    */
    //public synchronized void increment() {
    //    this.count++;
    //}

    // 위의 코드는 synchronized 블록(block)을 사용하여 다음과 같이 다시 작성할 수도 있다.
    //public void increment() {
    //    synchronized(this) {
    //        this.count++;
    //    }
    //}

}
