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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UberSeating {

}

class UberSeatingTest {
    final static UberSeating uber = new UberSeating();
    static void test1(){
        Set<Thread> riders = new HashSet<>();
        riders.addAll(create10Dems());
        riders.addAll(create14Reps());
    }

    private static Collection<? extends Thread> create10Dems() {
        Set<Thread> dems = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(()-> {
                uber.seatDem()
            };
        }
    }
}
