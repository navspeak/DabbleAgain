package multithreading;

import java.util.concurrent.*;

public class CountingSemaphore {
    private int usedPermits;
    private int permits;

    CountingSemaphore(int n){
        this.permits = n;
    }

    public synchronized void acquire() throws InterruptedException {
        while(usedPermits == permits)
            wait();
        usedPermits++;
        notify();

    }

    public synchronized void release() throws InterruptedException {
        while(usedPermits == 0)
            wait();
        usedPermits--;
        notify();

    }

    public static void testWithPingPong() throws InterruptedException {
        CountingSemaphore sem = new CountingSemaphore(1);

        Runnable printPing = () -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sem.acquire();
                    System.out.println("Ping " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable printPong = () -> {
            try {
                for (int i = 0; i < 5; i++) {
                    sem.release();
                    System.out.println("pong " + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

       Thread t1 = new Thread(printPing);
       Thread t2 = new Thread(printPong);
       t1.start();
       t2.start();
       t1.join();
       t2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        testWithPingPong();

    }

}
