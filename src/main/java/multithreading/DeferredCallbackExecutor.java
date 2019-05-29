package multithreading;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*

 */
public class DeferredCallbackExecutor {
    private static Random random = new Random(System.currentTimeMillis());

    // Minheap - holds the callback whose executeAt time is nearest
    private PriorityQueue<CallBack> q = new PriorityQueue<>();
    ReentrantLock lock = new ReentrantLock();
    Condition newCallbackArrived = lock.newCondition();

    public void start() throws InterruptedException {
        int lastInspectedQSize = 0;
        long sleepFor = 0;
        while(true) {
            lock.lock();
            while (q.isEmpty()) {
                newCallbackArrived.await();
            }

            if (lastInspectedQSize == q.size()){
                newCallbackArrived.await(sleepFor, TimeUnit.MILLISECONDS);
            }

            if (!q.isEmpty() && q.peek().executeAt >= System.currentTimeMillis()) {
                CallBack cb = q.poll();
                try {
                    System.out.println("Executed at " + System.currentTimeMillis() / 1000 + " required at " +
                            cb.executeAt / 1000 + ": message:" + cb.execute());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            sleepFor = q.isEmpty() ? 0: System.currentTimeMillis() - q.peek().executeAt;
            lastInspectedQSize = q.size();
            lock.unlock();
        }


    }
    public void registerCallBack(CallBack cb) {
        lock.lock();
        q.add(cb);
        newCallbackArrived.signal();
        lock.unlock();

    }



    public static void main(String[] args) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        final DeferredCallbackExecutor deferredCallbackExecutor = new DeferredCallbackExecutor();

        Thread service = new Thread(()->{
                try {
                    deferredCallbackExecutor.start();
                } catch (Exception e) {

                }
            },"Service_Thread");
        service.start();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    CallBack cb = new CallBack(1, () -> "Hello this is " + Thread.currentThread().getName());
                    deferredCallbackExecutor.registerCallBack(cb);
                }
            });
            thread.setName("Thread_" + (i + 1));
            thread.start();
            allThreads.add(thread);
            Thread.sleep((random.nextInt(3) + 1) * 1000);
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }
}

class CallBack implements Comparable<CallBack> {

    long executeAt;
    Callable<String> callable;

    public CallBack(long executeAfter, Callable callable) {
        this.executeAt = System.currentTimeMillis() + executeAfter * 1000;
        this.callable = callable;
    }

    public String execute() throws Exception {
        return callable.call();
    }

    @Override
    public int compareTo(CallBack o) {
        if (o.getClass() != this.getClass())
            return -1;
        return (int)(this.executeAt - o.executeAt);
    }
}