package MultiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//TO DO
//1.利用两个子线程实现指定范围的交替打印

public class CallableThreadTest_20220119 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i < 100; ++i) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        return i;
    }

    private static final Object lock = new Object();

    public static void main(String[] args) {
        CallableThreadTest_20220119 ctt = new CallableThreadTest_20220119();
        FutureTask<Integer> ft = new FutureTask<>(ctt);

        for (int i = 0; i < 100; ++i) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i的值为：" + i);
            if (i == 20) {
                new Thread(ft, "有返回值的子线程").start();
            }
        }
        try {
            System.out.println("子线程的返回值： " + ft.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
