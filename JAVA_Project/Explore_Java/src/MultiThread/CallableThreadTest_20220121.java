package MultiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableThreadTest_20220121 implements Callable<Integer> {

    private final int threadNo, threadCount, max;
    private static int cur_1 = 0, cur_2 = 0, runCount = 0;
    private static final Object lock = new Object();

    CallableThreadTest_20220121(int threadNo, int threadCount, int max) {
        this.threadNo = threadNo;
        this.threadCount = threadCount;
        this.max = max;
    }

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
                if (threadNo == 0) {
                    if (runCount == 0) {
                        while (cur_2 < 20) {
                            System.out.println("threadNo-" + threadNo + ": " + cur_2++);
                        }
                        ++runCount;
                    } else if (runCount == 1) {
                        while (cur_2 < max) {
                            System.out.println("threadNo-" + threadNo + ": " + cur_2++);
                        }
                    }
                } else if (threadNo == 1) {
                    int i = 0;
                    while (i < max) {
                        System.out.println("threadNo-" + threadNo + ": " + i++);
                    }
                }
                lock.notify();
            }
        }
        return cur_2;
    }

    public static void main(String[] args) {
        int threadCount = 2, max = 100;
        for (int i = 0; i < threadCount; ++i) {
            CallableThreadTest_20220120 ctt = new CallableThreadTest_20220120(i, threadCount, max);
            FutureTask<Integer> ft = new FutureTask<>(ctt);
            new Thread(ft).start();
        }
    }
}

