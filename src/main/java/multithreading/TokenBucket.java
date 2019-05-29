package multithreading;

/*
Imagine you have a bucket that gets filled with tokens at the rate of 1 token per second.
The bucket can hold a maximum of N tokens. Implement a thread-safe class that lets threads get a token when one is available.
If no token is available, then the token-requesting threads should block.
The class should expose an API called getToken that various threads can call to get a token
 */

import java.sql.Time;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class TokenBucket {
    int N;
    long lastRequestedTime;
    long availableTokens = 0;
    TokenBucket(int n){
        this.N = n;
        lastRequestedTime = System.currentTimeMillis();
    }
    synchronized void getKey() throws InterruptedException {
        availableTokens+= (System.currentTimeMillis() - lastRequestedTime) / 1000;
        //case: 1 - last token requested was more than N seconds before
        if (availableTokens > N)
            availableTokens = N;
        //case: 2 - All tokens consumed
        if (availableTokens == 0) {
            TimeUnit.SECONDS.sleep(1);
        } else {
            availableTokens--;
        }
        lastRequestedTime = System.currentTimeMillis();
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " +
                (System.currentTimeMillis() / 1000));
    }

    public static void testSurplusToken() throws InterruptedException, ExecutionException {
        TokenBucket tb = new TokenBucket(5);
        TimeUnit.SECONDS.sleep(10);
        Set<Callable<String>> tasks = new HashSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 15; i++) {
            tasks.add(()-> {
                try{
                    tb.getKey();
                }catch (InterruptedException ex){

                }
                return "success";
            });
        }
        final List<Future<String>> futures = executorService.invokeAll(tasks);
        executorService.shutdown();
        for (Future<String> f : futures) {
            f.get();
        }


    }

    public static void testwith1Token() throws InterruptedException, ExecutionException {
        TokenBucket tb = new TokenBucket(1);
        Set<Callable<String>> tasks = new HashSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 15; i++) {
            tasks.add(()-> {
                try{
                    tb.getKey();
                }catch (InterruptedException ex){

                }
                return "success";
            });
        }
        final List<Future<String>> futures = executorService.invokeAll(tasks);
        executorService.shutdown();
        for (Future<String> f : futures) {
            f.get();
        }


    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
           //testSurplusToken();
           testwith1Token();
//        Set<Thread> threads = new HashSet<>();
//        TokenBucket tb = new TokenBucket(5);
//        TimeUnit.SECONDS.sleep(10);
//        for (int i = 0; i < 12; i++) {
//            Thread t = new Thread(()-> {
//                try{
//                    tb.getKey();
//                }catch (InterruptedException ex){
//                    System.out.println("There's a problem");
//                }
//            }, "Thread_" + (i+1));
//            threads.add(t);
//        }
//
//        for(Thread t : threads){
//            t.start();
//        }
//
//        for(Thread t: threads){
//            t.join();
//        }
    }
}


