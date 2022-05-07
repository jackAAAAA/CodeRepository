package MultiThread.Liaoxuefeng;

public class P1_Main {

    /**
     * By extends Thread
     */
    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("Start a new thread!");
        }
    }

    /**
     * By implements Runnable
     */
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Start a new runnable thread!");
        }
    }

    public static void main(String[] args) {
//        MyThread myThread = new MyThread();
//        myThread.start();

//        Thread thread = new Thread(new MyRunnable());
//        thread.start();

//        Thread thread = new Thread(() -> System.out.println("Start a new lambda thread!"));
//        thread.start();

//        通过设置不同的休眠时间来调整线程的执行顺序
        System.out.println("main Start!");
        Thread t = new Thread() {
            public void run() {
                System.out.println("thread run...");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread end.");
            }
        };
        t.start();
        try {
            Thread.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end!");
    }
}
