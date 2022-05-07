package com.hencoder.threadsync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo implements TestDemo {
  private int x = 0;
  ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  Lock readLock = lock.readLock();
  Lock writeLock = lock.writeLock();

  private void count() {
    writeLock.lock();
    try {
      x++;
    } finally {
      writeLock.unlock();
    }
  }

  private void print() {
    readLock.lock();
    try {
      System.out.print(x + " ");
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public void runTest() {
    new Thread() {
      @Override
      public void run() {
        for (int i = 0; i < 10; ++i) {
//          改变print()和count()的顺序分别会输出：
//          1. 0 1 2 3 4 5 6 7 8 9
//          2. 1 2 3 4 5 6 7 8 9 10
          count();
          print();
        }
      }
    }.start();
  }
}