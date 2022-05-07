package MultiThread.Producer_Consumer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Producer_Consumer3 {
    public static void main(String[] args) throws InterruptedException {
        var taskQueue = new TaskQueue3();
//        var threadQueue = new ArrayList<Thread>();
        List<Thread> threadQueue = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            var dec = new Thread() {
//                @Override
//                public void run() {
//                    while (true) {
//                        try {
//                            String s = taskQueue.getTask();
//                            System.out.println("Execute Task: " + s);
//                        } catch (InterruptedException e) {
//                            return;
//                        }
//                    }
//                }
//            };
//            dec.start();
//            threadQueue.add(dec);
//        }
        for (int i = 0; i < 5; i++) {
            var dec = new Thread(() -> {
                while (true) {
                    try {
                        String s = taskQueue.getTask();
                        System.out.println("Consume task: " + s);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        return;
                    }
                }
            });
            dec.start();
            threadQueue.add(dec);
        }
//        var add = new Thread() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10; i++) {
//                    String s = "" + Math.random();
////                    这两行语句的顺序不能调换，否则会出现无法预期的结果
//                    System.out.println(i + 1 + "\nProduce task: " + s);
//                    taskQueue.addTask(s);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
        var add = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String s = "" + Math.random();
                System.out.println(i + 1 + "\nProduce task: " + s);
                taskQueue.addTask(s);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        add.start();
        add.join();
        Thread.sleep(100);
        for (var dec : threadQueue) {
            dec.interrupt();
        }
    }
}

class TaskQueue3 {
    Queue<String> q = new LinkedList<>();

    public synchronized void addTask(String s) {
        q.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (q.isEmpty()) {
            this.wait();
        }
        return q.remove();
    }
}
