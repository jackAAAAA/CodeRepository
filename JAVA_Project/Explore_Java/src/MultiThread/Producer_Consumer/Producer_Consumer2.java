package MultiThread.Producer_Consumer;

import java.util.LinkedList;
import java.util.Queue;

public class Producer_Consumer2 {
        public static void main(String[] args) throws InterruptedException {
            var q = new TaskQueue();
//        var ts = new ArrayList<Thread>();
//        for (int i = 0; i < 5; i++) {
            var t = new Thread(() -> {
                // 执行task:
//                如果不使用while (true)，那么消费者线程将无法重新获得锁而继续被执行；即顺序执行完以下代码，线程就将释放锁而结束
                while (true) {
                    try {
                        String s = q.getTask();
                        System.out.println("execute task: " + s + "\n");
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            });
            t.start();
//            ts.add(t);
//        }
        var add = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println(i + 1 + "\n    add task: " + s);
                q.addTask(s);
                try {
//                    本轮的add线程休眠100ms
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });
        add.start();
        add.join();
//        add.join() —— 生产者 & 消费者 交替打印的业务逻辑：
//        1.生产者线程（add线程）执行0-9的for循环因获得类锁而执行往队列中添加元素；添加元素之后会执行notifyAll()唤醒处于wait()状态的所有消费者线程
//        2.其中一个被唤醒的消费者线程执行队列中元素移除，由于消费者线程为while(true)的死循环 & 持有锁，会继续执行while(queue.isEmpty())中的wait()而释放锁
//        3.循环执行1 =》2 =》1，直到生产者 & 消费者线程执行完10次对应的 配套打印 为止
//        4.notify()/notifyAll()唤起处于等待状态的线程时，也都会释放锁
//        主线程main()休眠100ms
        Thread.sleep(100);
//        for (var t : ts) {
//            用了while(true)，则要在末尾主动结束线程
            t.interrupt();
//        用这行语句不会使得消费者线程停止运行
//        t.join();
//        }
    }

//    2022.03.13
//    public static void main(String[] args) throws InterruptedException {
//        var taskQueue = new TaskQueue();
//        var get = new Thread(() -> {
//            while (true) {
//                try {
//                    String s = taskQueue.getTask();
//                    System.out.println("execute -" + s);
//                } catch (Exception e) {
////                    return; //                    不抛异常
//                    e.printStackTrace();//                    抛异常
//                }
//            }
//        });
//        get.start();
//        var add = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                var s = new String() + Math.random();
//                taskQueue.addTask(s);
//                System.out.println(i + 1 + "\n add -" + s);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        add.start();
////        try {
////            add.join();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//        add.join();
////        try {
////            Thread.sleep(100);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        Thread.sleep(100);
////        try {
////            get.interrupt();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        get.interrupt();
//    }

}

class TaskQueue {
    private Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        queue.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
////                e.printStackTrace();
//                return null;
//            }
            this.wait();
        }
        return queue.remove();
    }

}

