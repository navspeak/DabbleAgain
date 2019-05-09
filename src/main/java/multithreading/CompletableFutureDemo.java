package multithreading;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
//https://www.baeldung.com/java-completablefuture
public class CompletableFutureDemo {
    public Future<String> calculateAsync() throws InterruptedException {
      CompletableFuture<String> completableFuture =
              new CompletableFuture<>();

      Executors.newCachedThreadPool().submit(() -> {
          TimeUnit.SECONDS.sleep(10);
          completableFuture.complete("Hello");
          return null;
      });
      // LocalTime time = LocalTime.now()
      return completableFuture;
    }

    public Future<String> calculateAsync1() throws InterruptedException {
        CompletableFuture<String> completableFuture =
               CompletableFuture.supplyAsync(this::readFromALineFromAFile)
                .thenCompose(s -> CompletableFuture.supplyAsync(()-> s + "!!"));

//        CompletableFuture<String> completableFuture1
//                = CompletableFuture.supplyAsync(() -> "Hello");
//
//        CompletableFuture<String> future = completableFuture1
//                .thenApply(s -> s + " World");

        return completableFuture;
    }




    /*
    Suppose we didn’t manage to find a result and decided to cancel an asynchronous execution altogether.
    This can be done with the Future‘s cancel method.
    This method receives a boolean argument mayInterruptIfRunning,
    but in the case of CompletableFuture it has no effect,
    as interrupts are not used to control processing for CompletableFuture.

     */

    public Future<String> calculateAsyncWithCancellation(){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(false);
            return null;
        });

        return completableFuture;
    }

    public String readFromALineFromAFile() {
        try {
            TimeUnit.SECONDS.sleep(5); // takes 5 seconds to read a file
        } catch (InterruptedException e) {}
        return "Line 1 from  a file";
    }



}
