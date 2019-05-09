package designPattern.structural;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Adaptor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(()-> System.out.println("Some task"));
    }
}
