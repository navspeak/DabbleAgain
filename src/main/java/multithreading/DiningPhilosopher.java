package multithreading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class DiningPhilosopher {
/*
forks[id]
forks[(id+4) % 5]
*/
    Semaphore[] forks = {new Semaphore(1), new Semaphore(1), new Semaphore(1),
        new Semaphore(1), new Semaphore(1)};
    Semaphore diners = new Semaphore(4);

    public void start(int id) throws InterruptedException {
        while(true){
            contemplate();
            eat(id);
        }
    }

    private void eat(int id) throws InterruptedException {
        diners.acquire();
        forks[id].acquire();
        forks[id+1].acquire();
        System.out.println("Philospher " + id + " is eating");
        forks[id].release();
        forks[id+1].release();
        diners.release();
    }

    private void contemplate() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test() throws InterruptedException {
        final DiningPhilosopher dp = new DiningPhilosopher();
        List<Thread> philosphers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                try {
                    dp.start(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Philosopher-"+id);
            philosphers.add(t);
        }

        Collections.shuffle(philosphers);

        philosphers.stream().forEach(p->p.start());
        for (Thread philosopher : philosphers) {
           philosopher.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        test();
    }

}
