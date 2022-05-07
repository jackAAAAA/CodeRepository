package MultiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableThreadTest_20220120 implements Callable<Integer> {

    private final int threadNo, threadCount, max;
    private static int cur_1 = 0, cur_2 = 0;
    private static final Object lock = new Object();

    CallableThreadTest_20220120(int threadNo, int threadCount, int max) {
        this.threadNo = threadNo;
        this.threadCount = threadCount;
        this.max = max;
    }

//TO DO
//1.利用两个子线程实现指定范围的交替打印
//2.已实现功能1，待深刻理解
    @Override
    public Integer call() throws Exception {
        while (cur_2 < max) {
            synchronized (lock) {
                if (cur_1 % threadCount != threadNo) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ++cur_1;
                if (threadNo == 0 && cur_2 < 20) {
                    while (cur_2 < 20) {
                        System.out.println("threadNo-" + threadNo + ": " + cur_2++);
                    }
                } else if (threadNo == 1) {
                    int i = 0;
                    while (i < max) {
                        System.out.println("threadNo-" + threadNo + ": " + i++);
                    }
                } else {
                    while (cur_2 < max) {
                        System.out.println("threadNo-" + threadNo + ": " + cur_2++);
                    }
                }
                lock.notify();
            }
        }
        return cur_2;
    }

    public static void main(String[] args) {
        int threadCount = 2, max = 1000;
        for (int i = 0; i < threadCount; ++i) {
            CallableThreadTest_20220120 ctt = new CallableThreadTest_20220120(i, threadCount, max);
            FutureTask<Integer> ft = new FutureTask<>(ctt);
            new Thread(ft).start();
        }
    }
}
