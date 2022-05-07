package MultiThread.Liaoxuefeng;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 和 ReentrantLock 获得锁和释放锁的方式不同，不能混合使用
 */

public class P9_Main {
    public static void main(String[] args) {
        Count count = new Count();
        new Thread() {
            public void run() {
                try {
                    count.add(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            public void run() {
                count.addd(2);
            }
        }.start();
    }
    static class Count {
        private int m = 0;
        private final Lock lock = new ReentrantLock();

        public void add(int n) throws Exception {
//            lock.lock();
//            ReentrantLock可以通过tryLock()尝试获取锁
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println("获取锁");
                try {
                    m += n;
                    System.out.println("thread1等待3秒");
                    Thread.sleep(3000);
                    System.out.println("thread1结束3秒");
                } finally {
                    lock.unlock();
                    System.out.println("释放锁");
                }
            }
        }

        public void addd(int n) {
            lock.lock();
            try {
                System.out.println("获取锁222");
                m += n;
                System.out.println("释放锁222");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.lock();
            }
        }

        public void getM() {
            System.out.println(m);
        }
    }
}

