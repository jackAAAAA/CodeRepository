package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 项目名:    Demo
 * 包名       thread
 * 文件名:    TryLockTest
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:19
 * 描述:     TODO
 */
public class TryLockTest {

    Lock lock = new ReentrantLock();
    List<String> list = new ArrayList();


    public void print() throws InterruptedException {
        if (lock.tryLock(50, TimeUnit.MILLISECONDS)) {
            try {
                System.out.println(Thread.currentThread().getName() + "拿到锁");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "没拿到锁");
        }
    }

    public static void main(String[] args) {
        TryLockTest lockDemo = new TryLockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        lockDemo.print();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + "异常");
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        lockDemo.print();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + "异常");
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        lockDemo.print();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + "异常");
                    }
                }
            }
        }).start();

    }
}
