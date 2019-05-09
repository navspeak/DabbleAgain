package multithreading;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Demo {
    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<Integer> q = new BoundedQueue<>(Integer.class, 5);
        Runnable produce50NumbersTask = () -> {
            try {
                for (int i = 0; i < 50; i++) {
                    q.enqueue(Integer.valueOf(i));
                    System.out.println("Producer Thread" + Thread.currentThread().getName()
                            + " enqueued " + i);
                }
            } catch (InterruptedException ie) {

            }
        };
        Runnable consume25NumberTask = () -> {
            try {
                for (int i = 0; i < 25; i++) {
                    System.out.println("Consumer Thread " +
                            Thread.currentThread().getName() +
                            " dequeued: " + q.dequeue());
                }
            } catch (InterruptedException ie) {

            }
        };
        Thread producerThread = new Thread(produce50NumbersTask);
        Thread consumerThread1 = new Thread(consume25NumberTask);
        Thread consumerThread2 = new Thread( consume25NumberTask);

        producerThread.start();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {

        }
        consumerThread1.start();
        consumerThread1.join();
        consumerThread2.start();
        producerThread.join();
        consumerThread2.join();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(produce50NumbersTask);
        System.out.println("Waiting for 3 sec");
        TimeUnit.SECONDS.sleep(3);
        executorService.submit(consume25NumberTask);
        executorService.submit(consume25NumberTask);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

    }
}

class BoundedQueue<T>{
    T[] items;
    int head = 0, tail = 0;
    int size = 0;
    final int capacity;

    BoundedQueue(Class<T> clazz, int capacity) {
        items = (T[]) Array.newInstance(clazz, capacity);
        this.capacity = capacity;
    }


    synchronized void enqueue(T item) throws InterruptedException {
        while (size == capacity) {
            wait();
        }
        if (tail == capacity)
            tail = 0;
        items[tail++] = item;
        size++;
        notify();
    }

    synchronized T dequeue() throws InterruptedException {
        while (size == 0){
            wait();
        }

        if (head == capacity){
            head = 0;
        }

        T ret = items[head];
        items[head] = null;
        head++;
        size--;
        notify();
        return ret;
    }

}