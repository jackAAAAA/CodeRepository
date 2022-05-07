package MultiThread.Producer_Consumer;

import java.util.LinkedList;
import java.util.Queue;

public class Producer_Consumer_0315 {

//    var修饰变量时，不能放在类中作为全局变量；只能放在函数中做局部变量
    //    TaskQueue4 q = new TaskQueue4();

    public static void main(String[] args) throws InterruptedException {
        var q = new TaskQueue4();
        var dec = new Thread(() -> {
            while (true) {
                try {
                    String s = q.consume();
                    System.out.println("Execute task: " + s);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        dec.start();
        var inc = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String s = "" + Math.random();
                System.out.println(i + 1 + "\nProduce task: " + s);
                q.produce(s);
//                让Thread_inc休眠的目的：确保生产者和消费者能够按照既定的顺序来交替执行
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        inc.start();
        inc.join();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dec.interrupt();
    }
}

class TaskQueue4 {
    private Queue<String> q = new LinkedList<>();

//    var test = new ArrayList<String>();
    public synchronized void produce(String s) {
//        var test = new ArrayList<String>();
        q.offer(s);
        this.notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (q.isEmpty()) {
            this.wait();
        }
        return q.remove();
    }
}
