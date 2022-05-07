package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/*
 * 项目名:    Demo
 * 包名       thread
 * 文件名:    LockTest
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:19
 * 描述:     TODO
 */
public class LockTest {

    Lock lock = new ReentrantLock();


    public void print() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockTest lockDemo = new LockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    lockDemo.print();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    lockDemo.print();
                }
            }
        }).start();
    }
}
