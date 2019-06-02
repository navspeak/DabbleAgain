package multithreading;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class ThreeThreads {
    public static void main(String[] args) {
        OnePrints123_TwoPrints456_ThreePrints_789_SoOn();
        threeThreadsPrint123Respectively();
    }

    private static void OnePrints123_TwoPrints456_ThreePrints_789_SoOn() {
        Set<Thread> threads = new HashSet<>(3);
        Object lock = new Object();
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new PrintTask1(i, lock), "Thread-"+ (i+1));
            threads.add(t);
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void threeThreadsPrint123Respectively() {
        Set<Thread> threads = new HashSet<>(3);
        Object lock = new Object();
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new PrintTask2(i), "Thread-"+ i);
            threads.add(t);
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}

class PrintTask1 implements Runnable{
    int threadId;
    Object lock;
    private static int sharedOutput = 0;

    public PrintTask1(int threadId, Object lock) {
        this.threadId = threadId;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (sharedOutput == 17) break;
                if (sharedOutput % 3 == threadId) {
                    System.out.println(Thread.currentThread().getName() + " prints " + ++sharedOutput);
                }
            }
        }
    }
}

class PrintTask2 implements Runnable{
    //T0 - 1,1,1.. T1-2,2,2... T3-3,3,3
    int threadId;
    private static int sharedOutput = 0;
    private static Semaphore one = new Semaphore(1);
    private static Semaphore two = new Semaphore(0);
    private static Semaphore three = new Semaphore(0);
    private static int count = 0;

    //For lock condition solution
    private static Object lock = new Object();
    private static int numToPrint = 0;

    public PrintTask2(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run() {
        printNumbers_Semaphore();
        printNumbers_Lock();
    }

    private void printNumbers_Semaphore() {
        while(count < 20) {
            count++;
            try {
                if (threadId == 0) {
                    one.acquire();
                    System.out.println(Thread.currentThread().getName() + " - 1");
                    two.release();
                } else if (threadId == 1) {
                    two.acquire();
                    System.out.println(Thread.currentThread().getName() + " - 2");
                    three.release();
                } else {
                    three.acquire();
                    System.out.println(Thread.currentThread().getName() + " - 3");
                    one.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printNumbers_Lock() {
        while(count < 20) {
            synchronized (lock) {
                if (threadId == numToPrint) {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " - " + ++numToPrint);
                    if (numToPrint == 3) numToPrint = 0;
                }
            }
        }
    }

}

