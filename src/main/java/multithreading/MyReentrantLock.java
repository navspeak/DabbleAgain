package multithreading;

public class MyReentrantLock {

    int lockCount = 0;
    String lockedBy;

    public synchronized void lock() throws InterruptedException {
        while (lockedBy != Thread.currentThread().getName() && lockCount > 0){
            wait();
        }
        lockedBy = Thread.currentThread().getName();
        lockCount++;

    }

    public synchronized void unlock(){
       if (lockedBy == Thread.currentThread().getName())
           lockCount--;
       if (lockCount == 0){
           lockedBy = null;
           notify();
       }
    }


}

