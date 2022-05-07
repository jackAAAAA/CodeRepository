package com.hencoder.threadinteraction;

public class ThreadInteractionDemo implements TestDemo {
  @Override
  public void runTest() {
    Thread thread = new Thread() {
      @Override
      public void run() {
        for (int i = 0; i < 1_000_000; i++) {
          if (isInterrupted()) {
            return;
//            break;
          }
//          try {
//            Thread.sleep(10000);
//          } catch (InterruptedException e) {
//            return;
//          }
          System.out.println("number: " + i);
        }
      }
    };
    thread.start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    thread.interrupt();
  }
}