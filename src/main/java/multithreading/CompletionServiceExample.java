package multithreading;

import java.util.Arrays;
import java.util.concurrent.*;

public class CompletionServiceExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		Task<String> task1 = new Task<>("task1");
		Task<String> task2 = new Task<>("task2");
		Task<String> task3 = new Task<>("task3");
		Task<String> task4 = new Task<>("task4");
		Task<String> task5 = new Task<>("task5");
		executorService.invokeAll(Arrays.asList(task1,task2,task3,task5));

		CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
		
		Future<String> f1 = completionService.submit(task1);
		Future<String> f2 = completionService.submit(task2);
		Future<String> f3 = completionService.submit(task3);
		Future<String> f4 = completionService.submit(task4);
		Future<String> f5 = completionService.submit(task5);
		
		for (int i = 0; i < 5;i++) {
			System.out.println(completionService.take().get());
		}
		
	}
	
	public static class Task<T> implements Callable<String> {
		String taskName;
		
		public Task(String name) {
			taskName = name;
		}

		@Override
		public String call() throws Exception {
			System.out.println(Thread.currentThread().getName() + " is running " + taskName);
			TimeUnit.SECONDS.sleep(30);
			System.out.println(Thread.currentThread().getName() + " has run " + taskName);
			return taskName+" Finished";
		}
		
	}

	public static class Task1<T>  {
		String taskName;

		public Task1(String name) {
			taskName = name;
		}


		public String doTask() throws Exception {
			System.out.println(Thread.currentThread().getName() + " is running " + taskName);
			TimeUnit.SECONDS.sleep(30);
			System.out.println(Thread.currentThread().getName() + " has run " + taskName);
			return taskName+" Finished";
		}

	}
}
