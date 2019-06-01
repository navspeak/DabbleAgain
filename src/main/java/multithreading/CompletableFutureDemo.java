package multithreading;

import java.time.LocalTime;
import java.util.concurrent.*;

//https://www.baeldung.com/java-completablefuture
public class CompletableFutureDemo {
    static CompletableFutureDemo demo = new CompletableFutureDemo();
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

    public void test1(){
        try {
            Future<String> completableFuture = demo.calculateAsync(); // won't block
            String result = completableFuture.get(); // block
            System.out.println(result.equals("Hello") ? "Pass": "Fail");

        } catch (InterruptedException  | ExecutionException e) {
            e.printStackTrace();
        }
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

    public void test2(){
        try {
            Future<String> completableFuture = CompletableFuture.completedFuture("DummyHello");
            String result = completableFuture.get();
            System.out.println(result.equals("DummyHello") ? "Pass": "Fail");
        } catch (InterruptedException  | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void test3() throws Exception{
        Future<String> completableFuture = demo.calculateAsync1();
        String result = completableFuture.get();
        System.out.println(result.equals("Line 1 from  a file!!") ? "Pass": "Fail");
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
//    @Rule
//    public ExpectedException exception = ExpectedException.none();
//    @Test
//    public void testException() throws Throwable{
//        exception.expect(CancellationException.class);
//        //exception.expectMessage("test");
//        Future<String> completableFuture = demo.calculateAsyncWithCancellation();
//        completableFuture.get();
//
//    }

    public String readFromALineFromAFile() {
        try {
            TimeUnit.SECONDS.sleep(5); // takes 5 seconds to read a file
        } catch (InterruptedException e) {}
        return "Line 1 from  a file";
    }





}
