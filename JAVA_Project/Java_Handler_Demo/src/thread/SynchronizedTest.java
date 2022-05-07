package thread;

/*
 * 项目名:    Demo
 * 包名       thread
 * 文件名:    SynchronizedTest
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:19
 * 描述:     TODO
 */
public class SynchronizedTest {
    static final Object object = new Object();

    public static void main(String[] args) {
        new Thread(new Creater()).start();
        new Thread(new Customer()).start();
    }

    public static class Creater implements Runnable {
        int i = 10;

        @Override
        public void run() {
            synchronized (object) {
                while (i > 0) {
                    System.out.println("生产" + i);
                    i--;
                    object.notify();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class Customer implements Runnable {
        int i = 10;

        @Override
        public void run() {
            synchronized (object) {
                while (i > 0) {
                    System.out.println("消费" + i);
                    i--;
                    object.notify();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
