package com.hencoder.threadsync;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();

//        runSynchronized1Demo();
//        runSynchronized2Demo();
//        runReadWriteLockDemo();
//        TO DO
        runSynchronized3Demo();
    }

    /**
     * 使用 Thread 类来定义工作
     */
    static void thread() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                System.out.println("Thread started!");
//            }
//        };
//        thread.start();
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread has started!");
            }
        };
        thread.start();
    }

    /**
     * 使用 Runnable 类来定义工作
     */
    static void runnable() {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Thread with Runnable started!");
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable has started!!!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    static void threadFactory() {
//        ThreadFactory factory = new ThreadFactory() {
//            AtomicInteger count = new AtomicInteger(0); // int
//
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r, "Thread-" + count.incrementAndGet()); // ++count
//            }
//        };

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + " started!");
//            }
//        };

//        Thread thread = factory.newThread(runnable);
//        thread.start();
//        Thread thread1 = factory.newThread(runnable);
//        thread1.start();
//        ThreadFactory：可以指定线程的名称
        ThreadFactory factory = new ThreadFactory() {
            final AtomicInteger count = new AtomicInteger(-1);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "Thread-" + count.incrementAndGet());
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "has started!!!");
            }
        };

        Thread thread1 = factory.newThread(runnable);
        thread1.start();
        Thread thread = new Thread(runnable);
        thread.start();

    }

    static void executor() {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Thread with Runnable started!");
//            }
//        };
//
//        Executor executor = Executors.newCachedThreadPool();
//        executor.execute(runnable);
//        executor.execute(runnable);
//        executor.execute(runnable);

//        ExecutorService myExecutor = new ThreadPoolExecutor(5, 100,
//                5, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());
//
//        myExecutor.execute(runnable);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable_executor has started!!!");
            }
        };

        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        ExecutorService myExecutor = new ThreadPoolExecutor(5, 100, 5, TimeUnit.MINUTES,
                new SynchronousQueue<Runnable>());
        myExecutor.execute(runnable);
    }

    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "I have done!!!";
            }
        };
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);
        while (true) {
            if (future.isDone()) {
                try {
                    String result = future.get();
                    System.out.println("result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

//        Callable<String> callable = new Callable<String>() {
//            @Override
//            public String call() {
//                try {
//                    Thread.sleep(1500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return "Done!";
//            }
//        };


//        ExecutorService executor = Executors.newCachedThreadPool();
//        Future<String> future = executor.submit(callable);
//        while (true) {
//            if (future.isDone()) {
//                try {
//                    String result = future.get();
//                    System.out.println("result: " + result);
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }

    }

    static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }

    static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

    static void runReadWriteLockDemo() {
        new ReadWriteLockDemo().runTest();
    }
}
