package MultiThread.Liaoxuefeng;

import java.util.concurrent.*;

public class P14_Main {
    public static void main(String[] args) throws Exception {
//        ExecutorService es = Executors.newFixedThreadPool(6);
//        ExecutorService es = new ThreadPoolExecutor(4, 10, 60,
//                TimeUnit.SECONDS, new SynchronousQueue<>());
        ScheduledExecutorService es = new ScheduledThreadPoolExecutor(5);
        for (int i = 0; i < 5; i++) {
//            es.submit(new Task("" + i));
//            1s后执行一次性任务
//            es.schedule(new Task("one-time"), 1, TimeUnit.SECONDS);
//            2s后开始执行定时任务，每3s执行一次
//            es.scheduleAtFixedRate(new Task("fixed-rate"), 2, 3, TimeUnit.SECONDS);

//            2s后开始执行定时任务，当任务执行完成后以3s为间隔来执行
            es.scheduleWithFixedDelay(new Task("fixed-delay"), 2, 3, TimeUnit.SECONDS);
        }
//      shutdown()：会等待正在执行的任务完成，再关闭；不过如果不是正在执行任务，而是等待被执行的任务，
//      那么等待被执行的任务都不会被执行
        es.shutdown();
//        Thread.sleep(10000);
    }

    static class Task implements Runnable {

        private final String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("start task " + name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("end task " + name);
        }
    }
}
