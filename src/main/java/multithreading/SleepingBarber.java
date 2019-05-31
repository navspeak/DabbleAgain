package multithreading;
/*
A barbershop consists of a waiting room with n chairs, and a barber chair for giving haircuts.
If there are no customers to be served, the barber goes to sleep.
If a customer enters the barbershop and all chairs are occupied, then the customer leaves the shop.
If the barber is busy, but chairs are available, then the customer sits in one of the free chairs.
If the barber is asleep, the customer wakes up the barber. Write a program to coordinate the interaction between
the barber and the customers.
 */

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SleepingBarber {

    final int CHAIRS = 3;
    int hairCutsGiven = 0;
    int waitingCustomers = 0;
    Semaphore customerArrive = new Semaphore(0);
    Semaphore barberReady = new Semaphore(0);
    Semaphore hairCutDone = new Semaphore(0);
    Semaphore customerLeave = new Semaphore(0);
    ReentrantLock lock = new ReentrantLock();


    void customer() throws InterruptedException {
        lock.lock();
        if (waitingCustomers == CHAIRS){
            System.out.println(Thread.currentThread().getName() + " is leaving. No chairs :-(");
            lock.unlock();
            return;
        }

        waitingCustomers++;
        customerArrive.release();
        if (waitingCustomers != 1) {
            System.out.println(Thread.currentThread().getName() + " is seated");
        }
        lock.unlock();

        barberReady.acquire();
        lock.lock();
        waitingCustomers--;
        System.out.println(Thread.currentThread().getName() + " is getting hair cut. count no. " + hairCutsGiven);
        lock.unlock();

        hairCutDone.acquire();
        customerLeave.release();
    }

    void barber() throws InterruptedException {

        while (true) {
            customerArrive.acquire();
            barberReady.release();
            hairCutsGiven++;
            System.out.println("Barber giving count no. " + hairCutsGiven);
            TimeUnit.SECONDS.sleep(1);
            hairCutDone.release();
            customerLeave.acquire();
        }
    }

    public static void runTest() throws InterruptedException {

        HashSet<Thread> set = new HashSet<>();
        final SleepingBarber barberShopProblem = new SleepingBarber();

        Thread barberThread = new Thread(() -> {
            try {
                barberShopProblem.barber();
            } catch (InterruptedException ie) {

            }
        }, "Barber");
        barberThread.start();

        String[] customers = {"Bob", "Rob", "Tom", "Tim", "Sam",
                "Ram", "Zoe", "Jon", "Dan", "San"};
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    barberShopProblem.customer();
                } catch (InterruptedException ie) {

                }
            }, customers[i]);
            set.add(t);
        }

        set.stream().forEach(Thread::start);
        set.stream().forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        set.clear();
        Thread.sleep(500);

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        barberShopProblem.customer();
                    } catch (InterruptedException ie) {

                    }
                }
            }, customers[i]);
            set.add(t);
        }
        set.stream().forEach(Thread::start);
        set.stream().forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        runTest();
    }
}
