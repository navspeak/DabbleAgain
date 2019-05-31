package multithreading;

/*
Imagine at the end of a political conference, republicans and democrats are trying to leave the venue and ordering Uber
rides at the same time. However, to make sure no fight breaks out in an Uber ride, the software developers at Uber
come up with an algorithm whereby either an Uber ride can have all democrats or republicans or two Democrats and two
Republicans. All other combinations can result in a fist-fight.

Your task as the Uber developer is to model the ride requestors as threads that call the method seated once a right
combination is found and then any one of the threads tells the Uber driver to drive.
This could be any of the four threads.
 */


import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberSeating {
    CyclicBarrier barrier = new CyclicBarrier(4);
    int dems, reps;
    Semaphore demsWaiting = new Semaphore(0);
    Semaphore repsWaiting = new Semaphore(0);
    ReentrantLock lock = new ReentrantLock();

    public void seatDemocrat() throws InterruptedException, BrokenBarrierException {
        boolean rideLeader=false;
        lock.lock();
        dems++;
        if(dems==4){
            demsWaiting.release(3);
            rideLeader =true;
            dems-=4;

        } else if (dems==2 && reps>=2){
            demsWaiting.release(1);
            repsWaiting.release(2);
            dems-=2;
            reps-=2;
            rideLeader =true;

        } else {
            lock.unlock();
            demsWaiting.acquire();
        }
        seat();
        barrier.await();

        if (rideLeader == true){
            drive();
            lock.unlock();
        }

    }



    private void drive() {
        System.out.println("Driving with rideLeader " + Thread.currentThread().getName());
    }

    private void seat() {
        System.out.println("Seated " + Thread.currentThread().getName());
    }

    public void seatRepublican() throws BrokenBarrierException, InterruptedException {
        boolean rideLeader = false;
        lock.lock();
        reps++;
        if (reps == 4){
            repsWaiting.release(3);
            reps-=4;
            rideLeader = true;
        }else if (reps==2 && dems >=2){
            repsWaiting.release(1);
            demsWaiting.release(2);
            reps-=2;
            dems-=2;
            rideLeader = true;
        }else {
            lock.unlock();
            repsWaiting.acquire();
        }


        seat();
        barrier.await();

        if (rideLeader == true){
            drive();
            lock.unlock();
        }

    }



}

class UberSeatingTest {
    private final static UberSeating uber = new UberSeating();
    static void test() throws InterruptedException {
        List<Thread> passengers = new ArrayList<>();
        passengers.addAll(createThreads(10, "DEM"));
        passengers.addAll(createThreads(14, "REP"));
        Collections.shuffle(passengers);

        for (Thread t : passengers) {
            t.start();
        }

        for (Thread t : passengers) {
           t.join();
        }
    }

    private static Collection<? extends Thread> createThreads(final int n, String demOrRep) {
        Set<Thread> passengers = new HashSet<>();
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(() -> {
                try {
                    if (demOrRep.equals("DEM"))
                        uber.seatDemocrat();
                    else
                        uber.seatRepublican();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }, demOrRep.equals("DEM") ? "DEM_" + (i+1) : "REP_" + (i+1));
            passengers.add(t);
        }
        return passengers;
    }

    public static void main(String[] args) throws InterruptedException {
        test();
        //runTest();
    }

}
