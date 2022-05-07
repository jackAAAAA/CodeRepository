package com.hencoder.threadandroid;

public class Main {
  public static void main(String[] args) {
    final ThreadLocal<Integer> threadNumber = new ThreadLocal<>();

    new Thread() {
      @Override
      public void run() {
//        ...;
        threadNumber.set(1);
        System.out.println(threadNumber.get()); //1
      }
    }.start();
    new Thread() {
      @Override
      public void run() {
//        ...;
        threadNumber.set(2);
        System.out.println(threadNumber.get()); // 2
      }
    }.start();
  }
}
