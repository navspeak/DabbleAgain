package multithreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BarrierDemo  {
    public static void main( String args[] ) throws Exception{
        Barrier.runTest();
    }
}

class Barrier {

    final int totalThreads;
    int count;
    int released = 0;
    final Runnable runnable;

    public Barrier(int totalThreads, Runnable runnable) {
        this.totalThreads = totalThreads;
        this.runnable = runnable;
    }

    public static void runTest() throws InterruptedException {
        //final Barrier barrier = new Barrier(3, ()-> System.out.println("Barrier Reached " + Thread.currentThread().getName()));
        // Barrier action always runs in last thread reaching the barrier
        final CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("Barrier action " +
                Thread.currentThread().getName()));

        Thread t1 = new Thread(()->{
            try {
                System.out.println("One Thread has arrived - " + Thread.currentThread().getName());
                barrier.await();
                System.out.println("Two Threads have arrived - " + Thread.currentThread().getName());
                barrier.await();
                System.out.println("Three Threads has arrived - " + Thread.currentThread().getName());
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (BrokenBarrierException e) {
                System.out.println("Barrier was broken " + Thread.currentThread().getName());
            }
        }, "Thread 1");

        Thread t2 = new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("One Thread has arrived - " + Thread.currentThread().getName());
                barrier.await();
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("Two Threads have arrived - " + Thread.currentThread().getName());
                barrier.await();
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("Three Threads has arrived - " + Thread.currentThread().getName());
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (BrokenBarrierException e) {
                System.out.println("Barrier was broken " + Thread.currentThread().getName());
            }
        }, "Thread 2");

        Thread t3 = new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(800);
                System.out.println("One Thread has arrived - " + Thread.currentThread().getName());
                barrier.await();
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("Two Threads have arrived - " + Thread.currentThread().getName());
                barrier.await();
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("Three Threads has arrived - " + Thread.currentThread().getName());
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (BrokenBarrierException e) {
                System.out.println("Barrier was broken " + Thread.currentThread().getName());
            }
        }, "Thread 3");

//        Thread t4 = new Thread(()-> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500, 900));
//                barrier.reset();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
       t1.start();
       t2.start();
       t3.start();
       //t4.start();
       t1.join();
       t2.join();
       t3.join();
       //t4.join();
    }


    public synchronized void await() throws InterruptedException {

        // t1,t2,t3
        // t1, t2 arrive and count = 2 < 3 => thus they wait at line#127
        // t3 arrive and count = 3. All waiting threads must be notified at line#124
        //  => Till this point t1 and t2 are just waiting
        //  => So we when t3 notifies, it sets released = 3
        //  => each thread when they exit, decrement released
        //  => only when released = 0 we set count = 0
        // So,
        // Say instead of t1 or t2, t3 again acquires the lock.
        // The count is 3 till now and we must wait till all threads
        // exit the await() method by calling notify when released = 0
        while(count == totalThreads)
            wait();
        count++;
        if (count == totalThreads) {
            notifyAll();
            released = totalThreads;
            runnable.run();
        } else {
            while(count < totalThreads)
                wait();
        }
        released--;
        if (released == 0) {
            count = 0;
            notifyAll();
        }


    }
}
