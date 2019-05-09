package multithreading;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class CompletableFutureDemoTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private static final CompletableFutureDemo demo = new CompletableFutureDemo();
    @Test
    public void test1(){
        try {
            Future<String> completableFuture = demo.calculateAsync(); // won't block
            String result = completableFuture.get(); // block
            assertEquals("Hello", result);

        } catch (InterruptedException  | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        try {
            Future<String> completableFuture = CompletableFuture.completedFuture("DummyHello");
            String result = completableFuture.get();
            assertEquals("DummyHello", result);
        } catch (InterruptedException  | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() throws Exception{
        Future<String> completableFuture = demo.calculateAsync1();
        String result = completableFuture.get();
        assertEquals("Line 1 from  a file!!", result);
    }

    @Test
    public void testException() throws Throwable{
        exception.expect(CancellationException.class);
        //exception.expectMessage("test");
        Future<String> completableFuture = demo.calculateAsyncWithCancellation();
        completableFuture.get();

    }

}