package com.hencoder.threadsync;

import java.util.concurrent.atomic.AtomicBoolean;

public class Synchronized1Demo implements TestDemo {
  private AtomicBoolean running = new AtomicBoolean(true);

  private void stop() {
    running.set(false);
  }

  @Override
  public void runTest() {
    new Thread() {
      @Override
      public void run() {
        while (running.get()) {
          System.out.println("HERE");
          System.out.println("HERE");
          System.out.println("HERE");
          break;
        }
      }
    }.start();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    stop();
  }
}
