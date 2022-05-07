package MultiThread.Liaoxuefeng;

import java.math.*;
import java.util.concurrent.*;

/**
 * Future + Callable获取任务返回的结果
 */

public class P15_Main {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(4);
        Callable<BigDecimal> task = new Task("601857");
        Future<BigDecimal> future = es.submit(task);
        System.out.println(future.get());
        es.shutdown();
    }

    static class Task implements Callable<BigDecimal> {

        public Task(String code) {
        }

        @Override
        public BigDecimal call() throws Exception {
            Thread.sleep(1000);
            double d = 5 + Math.random() * 20;
//            double d = 2.3365;
//            RoundingMode：用来指定小数的舍入方式
            return new BigDecimal(d).setScale(3, RoundingMode.DOWN);
        }
    }
}
