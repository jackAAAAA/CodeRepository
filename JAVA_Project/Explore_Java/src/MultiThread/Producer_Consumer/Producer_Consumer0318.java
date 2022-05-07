package MultiThread.Producer_Consumer;

import java.util.LinkedList;
import java.util.Queue;

public class Producer_Consumer0318 {
    public static void main(String[] args) throws InterruptedException {
        var taskQueue = new TaskQueue();
        var dec = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String s = taskQueue.consume();
                        System.out.println("Consume products: " + s);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };
        dec.start();
        var inc = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    String s = "" + Math.random();
                    System.out.println(i + 1 + "\nProduce products: " + s);
                    taskQueue.produce(s);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        inc.start();
        inc.join();
        Thread.sleep(100);
        dec.interrupt();
    }

    static class TaskQueue {
        private Queue<String> q = new LinkedList<>();

        public synchronized void produce(String s) {
            q.offer(s);
            this.notifyAll();
        }

        public synchronized String consume() throws InterruptedException {
            while (q.isEmpty()) {
                this.wait();
            }
            return q.poll();
        }
    }
}

