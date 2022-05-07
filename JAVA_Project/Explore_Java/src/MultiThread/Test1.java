package MultiThread;

import java.util.concurrent.atomic.AtomicInteger;

public class Test1 {

    /**
     * value1没有进行任何线程安全方面的保护，value2使用了乐观锁(CAS)，value3使用了悲观锁(synchronized)。
     * 运行程序，使用1000个线程同时对value1、value2和value3进行自增操作，
     * 可以发现：value2和value3的值总是等于1000，而value1的值常常小于1000(因为处于线程不安全的线程对value1的操作不同步（CPU的指令重排序），
     * 导致每个线程操作的value1实际是不一致的，因此value1的值总是小于1000（因为操作总次数才1000次，不可能大于1000）)。
     */

    //value1：线程不安全
    private static int value1 = 0;
    //value2：使用乐观锁
    private static AtomicInteger value2 = new AtomicInteger(0);
    //value3：使用悲观锁
    private static int value3 = 0;
    private static synchronized void increaseValue3(){
        value3++;
    }

    public static void main(String[] args) throws Exception {
        //开启1000个线程，并执行自增操作
        for(int i = 0; i < 1000; ++i){
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    value1++;
                    value2.getAndIncrement();
                    increaseValue3();
                }
            }).start();
        }
        //打印结果
//        Thread.sleep(1000);
        System.out.println("线程不安全：" + value1);
        System.out.println("乐观锁(AtomicInteger)：" + value2);
        System.out.println("悲观锁(synchronized)：" + value3);
    }
}
