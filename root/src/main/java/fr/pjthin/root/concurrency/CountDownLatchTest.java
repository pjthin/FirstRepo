package fr.pjthin.root.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CountDownLatchTest {

    public static void main(String[] args) {
        int count = 10;
        CountDownLatch cdl = new CountDownLatch(count);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.execute(new WaitingTask(cdl));
        IntStream.range(0, count)
                .mapToObj(i -> new PreTask(cdl, "task-" + i))
                .forEach(preTask -> pool.execute(preTask));

    }

    static class PreTask implements Runnable {

        private final CountDownLatch cdl;
        private final String task;

        @Override
        public void run() {
            try {
                System.out.println("Starting " + task);
                Thread.sleep(1000);
                cdl.countDown();
                System.out.println("End " + task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public PreTask(CountDownLatch cdl, String task) {
            this.cdl = cdl;
            this.task = task;
        }
    }

    static class WaitingTask implements Runnable {

        CountDownLatch cdl;

        @Override
        public void run() {
            try {
                System.out.println("Start waiting !");
                cdl.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("End waiting !");
        }

        public WaitingTask(CountDownLatch cdl) {
            this.cdl = cdl;
        }

    }

}
