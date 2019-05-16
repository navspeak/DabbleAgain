package multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class LatchDemo{
    public static void main(String[] args) throws InterruptedException {
        Latch.runTest();
    }
}

public class Latch {
    final int totalThreads;
    int count = 0;
    public static void runTest() throws InterruptedException {
        final Latch latch = new Latch(3);
        //final CountDownLatch latch = new CountDownLatch(3);


        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("First Thread has finished its work - " + Thread.currentThread().getName());
            latch.countDown();

        }, "Thread 1");


        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Second Thread has finished its work - " + Thread.currentThread().getName());
            latch.countDown();

        }, "Thread 2");


        Thread t3 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(4500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Third Thread has finished its work - " + Thread.currentThread().getName());
            latch.countDown();

        }, "Thread 3");

        t1.start();
        t2.start();
        t3.start();

        System.out.println("Waiting for all threads to finish it work");
        latch.await();
        System.out.println("Finally all threads've finished their work");
    }

    public Latch(int numThreads) {
        this.totalThreads = numThreads;
        this.count = numThreads;
    }

    public  synchronized void countDown() {
        count--;
        if (count == 0){
            notify();
        }

    }

    public synchronized void await() throws InterruptedException {
        while(count > 0) {
            wait();
        }
    }
}

