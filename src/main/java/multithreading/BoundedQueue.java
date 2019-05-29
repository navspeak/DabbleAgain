package multithreading;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Demo {
    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<Integer> q = new BoundedQueue<>(Integer.class, 5, true);
        Thread prodThread1 = new Thread(()->{
            for (int i = 0; i < 50; i++) {
                try {
                    q.enqueue(i+1);
                    System.out.printf(Thread.currentThread().getName() + " enquequed %d \n", i+1, q.size);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "prodThread1");


        Thread consThread1 = new Thread(()->{
            for (int i = 0; i < 25; i++) {
                try {
                    Integer ret = q.dequeue();
                    System.out.printf(Thread.currentThread().getName() + " dequeued %d \n", ret, q.size);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "consThread1");

        Thread consThread2 = new Thread(()->{
            for (int i = 0; i < 25; i++) {
                try {
                    Integer ret = q.dequeue();
                    System.out.printf(Thread.currentThread().getName() + " dequeued %d \n", ret, q.size);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "consThread2");

        prodThread1.start();
        TimeUnit.SECONDS.sleep(2);
        consThread1.start();
        consThread2.start();

        prodThread1.join();
        consThread1.join();
        consThread2.join();


    }
}

class BoundedQueue<T>{
    T[] items;
    int head = 0, tail = 0,  size = 0;
    final int capacity;
    public BoundedQueue(Class clazz, int capacity, boolean logging) {
        this.items = (T[])Array.newInstance(clazz, capacity);
        this.capacity = capacity;
    }


    public synchronized void enqueue(T item) throws InterruptedException {
        while (size == capacity) {
            wait();
        }
        if (tail == capacity)
            tail = 0;

        items[tail++] = item;
        size++;

        notify();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        if (head == capacity)
            head = 0;

        T ret = items[head];
        items[head++] = null;
        size--;
        notify();

        return ret;
    }


}

