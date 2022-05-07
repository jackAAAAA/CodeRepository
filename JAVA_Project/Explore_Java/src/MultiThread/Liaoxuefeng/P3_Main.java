package MultiThread.Liaoxuefeng;

public class P3_Main {

//    static class MyThread extends Thread{
//        @Override
//        public void run() {
//            int n = 0;
//            while (!isInterrupted()) {
//                ++n;
//                System.out.println(n + " Hello!");
//            }
//        }
//    }
//    public static void main(String[] args) {
//        Thread t = new MyThread();
//        t.start();
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t.interrupt();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("end!");
//    }

//    static class MyThread extends Thread{
//        @Override
//        public void run() {
//            Thread hello = new HelloThread();
//            hello.start();
//            try {
//                hello.join();
//            } catch (InterruptedException e) {
//                System.out.println("interrupted!");;
//            }
//            hello.interrupt();
//        }
//    }
//    static class HelloThread extends Thread{
//        @Override
//        public void run() {
//            int n = 0;
//            while (!isInterrupted()) {
//                ++n;
//                System.out.println(n + " Hello!");
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    break;
//                }
//            }
//        }
//    }
//    public static void main(String[] args) {
//        MyThread t = new MyThread();
//        t.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t.interrupt();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("main ends!");
//    }

    static class HelloThread extends Thread {
        public volatile boolean running = true;

        @Override
        public void run() {
            int n = 0;
            while (running) {
                ++n;
                System.out.println(n + " Hello!");
            }
            System.out.println("HelloThread ends!");
        }
    }

    public static void main(String[] args) {
        HelloThread t = new HelloThread();
        t.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }

}
