package multithreading;

/*
A bathroom is being designed for the use of both males and females in an office but requires the following constraints
to be maintained:

There cannot be men and women in the bathroom at the same time.
There should never be more than three employees in the bathroom simultaneously.
The solution should avoid deadlocks. For now, though, don’t worry about starvation.
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnisexBathroom {
    enum GENDER {MALE, FEMALE, NONE};
    GENDER inUseBy = GENDER.NONE;
    Semaphore maxPersons = new Semaphore(3);
    int numOfPersonsIn = 0;
    Lock lock = new ReentrantLock(true);
    Condition bathroomInUseCondition = lock.newCondition();

    void maleUseBathroom(String name) throws InterruptedException {
        try {
            lock.lock();
            while (inUseBy == GENDER.FEMALE)
                bathroomInUseCondition.await();
            maxPersons.acquire();
            numOfPersonsIn++;
            inUseBy = GENDER.MALE;
            bathroomInUseCondition.signalAll();
        } finally {
            lock.unlock();
        }

        useBathroom(name);
        maxPersons.release();
        try {
            lock.lock();
            numOfPersonsIn--;
            if (numOfPersonsIn == 0)
                inUseBy = GENDER.NONE;
            bathroomInUseCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    void femaleUseBathroom(String name) throws InterruptedException {
        try {
            lock.lock();
            while (inUseBy == GENDER.MALE)
                bathroomInUseCondition.await();
            maxPersons.acquire();
            numOfPersonsIn++;
            inUseBy = GENDER.FEMALE;
            bathroomInUseCondition.signalAll();
        } finally {
            lock.unlock();
        }
        useBathroom(name);
        maxPersons.release();
        try {
            lock.lock();
            numOfPersonsIn--;
            if (numOfPersonsIn == 0)
                inUseBy = GENDER.NONE;
            bathroomInUseCondition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    static void useBathroom(String name) throws InterruptedException {
        System.out.println(name + " is using the bathroom");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(name + " has finished using the bathroom");
    }

    public static void runTest() throws InterruptedException {

        final UnisexBathroom unisexBathroom = new UnisexBathroom();

        Thread Lisa = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.femaleUseBathroom("Lisa");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread John = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("John");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread Bob = new Thread(() -> {
            try {
                unisexBathroom.maleUseBathroom("Bob");
            } catch (InterruptedException ie) {

            }
        });

        Thread Anil = new Thread(() -> {
            try {
                unisexBathroom.maleUseBathroom("Anil");
            } catch (InterruptedException ie) {

            }
        });

        Thread Wentao = new Thread(() -> {
            try {
                unisexBathroom.maleUseBathroom("Wentao");
            } catch (InterruptedException ie) {

            }
        });

        Lisa.start();
        John.start();
        Bob.start();
        Anil.start();
        Wentao.start();

        Lisa.join();
        John.join();
        Bob.join();
        Anil.join();
        Wentao.join();

    }

    public static void main(String[] args) throws InterruptedException {
        runTest();
    }
}
