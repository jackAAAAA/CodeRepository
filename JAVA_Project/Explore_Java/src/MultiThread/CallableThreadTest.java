package MultiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableThreadTest implements Callable<Integer> {

    @Override
    public Integer call() {
        // call 方法即为线程的执行体，并且拥有返回值
        int i = 0;
        for (; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        return i;
    }

    public static void main(String[] args) {
        // 创建Callable实现体的实例，使用FutureTask类包装Callable对象
        CallableThreadTest ctt = new CallableThreadTest();
        FutureTask<Integer> ft = new FutureTask<>(ctt);
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i的值" + i);
            if (i == 20) {
                //使用FutureTask对象作为Thread对象的target创建并启动新线程
                new Thread(ft, "有返回值的子线程").start();
            }
        }
        try {
            //调用FutureTask对象的get()方法来获得子线程执行结束后的返回值
            System.out.println("子线程的返回值：" + ft.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
