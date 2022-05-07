package MultiThread.Liaoxuefeng;

import java.util.*;

/**
 * 通过Thread开线程除了将内部类单独列出以外，还可以通过匿名内部类 / lambda表达式来实现。
 */

public class P8_Main {
    static class TaskQueue {
        Queue<String> queue = new LinkedList<>();

        public synchronized void addTask(String s) {
            this.queue.add(s);
            this.notifyAll();
        }

        public synchronized String getTask() throws InterruptedException {
            while (queue.isEmpty()) {
                this.wait();
            }
            return this.queue.remove();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var q = new TaskQueue();
        var ts = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            var t = new Thread(() -> {
//                执行task
                while (true) {
                    try {
                        String s = q.getTask();
                        System.out.println("execute task: " + s);
                    } catch (InterruptedException e) {
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
