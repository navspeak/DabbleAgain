package multithreading;

class SynchronousExecutorDemo
 {
    public static void main( String args[] ) throws Exception {

        /* line 10-12 is given
        Problem is that main thread doesn't block for asych task
        Without changing Executor class solve this conundrum */
        Executor executor = new Executor();
        executor.asynchronousExecution(() -> {
            System.out.println("I am done");
        });

        System.out.println("main thread exiting...");

        //Solution
        SynchronousWrapper executor1 = new SynchronousWrapper();
        executor.asynchronousExecution(() -> {
            System.out.println("I am done");
        });

        System.out.println("main thread exiting...");
    }
}

@FunctionalInterface
interface Callback {
    public void done();
}


class SynchronousWrapper extends Executor {

    @Override
    public void asynchronousExecution(Callback callback) throws Exception {

        Object signal = new Object(); // effectively final
        final boolean[] isDone = new boolean[1]; // to make it effectively final

        Callback cbWrapper = new Callback() {

            @Override
            public void done() {
                callback.done();
                synchronized (signal) {
                    signal.notify();
                    isDone[0] = true;
                }
            }
        };

        // Call the asynchronous executor
        super.asynchronousExecution(cbWrapper);

        synchronized (signal) {
            while (!isDone[0]) {
                signal.wait();
            }
        }

    }
}

//given
class Executor {

    public void asynchronousExecution(Callback callback) throws Exception {

        Thread t = new Thread(() -> {
            // Do some useful work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
            }
            callback.done();
        });
        t.start();
    }
}

