package MultiThread.Liaoxuefeng;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock
 */

public class P11_Main {
    static class Counter {
        private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
        private final Lock rlock = rwlock.readLock();
        private final Lock wlock = rwlock.writeLock();
        private int[] counts = new int[3];

        public void inc(int index) {
            wlock.lock(); // 加写锁
            try {
                counts[index] += 1;
            } finally {
                wlock.unlock(); // 释放写锁
            }
        }

        public int[] get() {
            rlock.lock(); // 加读锁
            try {
                return Arrays.copyOf(counts, counts.length);
            } finally {
                rlock.unlock(); // 释放读锁
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Counter counter = new Counter();
//        写线程修改数据
        Thread wt1 = new Thread(() -> {
            counter.inc(0);
            counter.inc(0);
            counter.inc(1);
        });
//        多个读线程同时读取数据
        Thread rt1 = new Thread(() -> {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for (int a : counter.get()) {
                System.out.println("rt1 get(): " + a);
            }
        });
        Thread rt2 = new Thread(() -> {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for (int a : counter.get()) {
                System.out.println("rt2 get(): " + a);
            }
        });
        Thread rt3 = new Thread(() -> {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for (int a : counter.get()) {
                System.out.println("rt3 get(): " + a);
            }
        });

        wt1.start();
//        必须等写线程写完才能被读
        wt1.join();
        rt1.start(); rt2.start(); rt3.start();
        rt1.join(); rt2.join(); rt3.join();
    }
}
