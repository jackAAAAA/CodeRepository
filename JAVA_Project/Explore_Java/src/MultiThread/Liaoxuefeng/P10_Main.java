package MultiThread.Liaoxuefeng;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock锁利用Condition提供的await()、signal()、signalAll()
 * 实现synchronized锁利用对象的wait()、notify()、notifyAll()一样的效果，它们的作用是一一对应的。
 */

public class P10_Main {
    static class TaskQueue {
//        Queue<String> queue = new LinkedList<>();
//
//        public synchronized void addTask(String s) {
//            this.queue.add(s);
//            this.notifyAll();
//        }
//
//        public synchronized String getTask() throws InterruptedException {
//            while (queue.isEmpty()) {
//                this.wait();
//            }
//            return this.queue.remove();
//        }
        private final Lock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();
        private Queue<String> queue = new LinkedList<>();

        public void addTask(String s) {
            lock.lock();
            try {
                queue.add(s);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public String getTask() throws Exception {
            lock.lock();
            try {
                while (queue.isEmpty()) {
//                    condition.await();
                    if (condition.await(1, TimeUnit.SECONDS)) {
//                        如果在1s内被其他线程给唤醒
                        break;
                    } else {
//                        没有被其他线程唤醒
                        System.out.println("自己唤醒！");
                    }
                }
                return queue.remove();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var q = new TaskQueue();
        var ts = new ArrayList<Thread>();
        for (int i = 0; i < 6; i++) {
            var t = new Thread(() -> {
//                执行task
                while (true) {
                    try {
                        String s = q.getTask();
                        System.out.println("execute task: " + s);
                    } catch (Exception e) {
//                            e.printStackTrace();
                        return;
                    }
                }
            });
            t.start();
            ts.add(t);
        }
        var add = new Thread() {
            @Override
            public void run() {
//                测试condition的有限等待
                try {
                    Thread.sleep(1001);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10; i++) {
//                    放入task
                    String s = "t-" + Math.random();
                    System.out.println("add task: " + s);
                    q.addTask(s);
//                    保证供需平衡
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        add.start();
        add.join();
        Thread.sleep(100);
        for (var t : ts) {
            t.interrupt();
        }
    }
}
