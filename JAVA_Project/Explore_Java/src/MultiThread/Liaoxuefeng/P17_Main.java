package MultiThread.Liaoxuefeng;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join模式可以进行并行计算以提高效率。
 */
public class P17_Main {

//    递归
    static class SumTask extends RecursiveTask<Long> {
        static final int THRESHOLD = 500;
        long[] array;
        int start, end;

        SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += this.array[i];
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                return sum;
            }
            int mid = start + ((end - start) >> 1);
            System.out.println(String.format("split %d~%d ==> " +
                    "%d~%d, %d~%d", start, end, start, mid, mid, end));
            SumTask subtask1 = new SumTask(this.array, start, mid);
            SumTask subtask2 = new SumTask(this.array, mid, end);
            invokeAll(subtask1, subtask2);
            Long subresult1 = subtask1.join();
            Long subresult2 = subtask2.join();
            Long result = subresult1 + subresult2;
            System.out.println("result = " + subresult1 +
                    "+" + subresult2 + "==>" + result);
            return result;
        }
    }

    public static void main(String[] args) {
        long[] array = new long[2000];
        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        System.out.println("ExpectedSum sum: " + expectedSum);
//        fork/join:
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long begin = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long end = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result +
                " in " + (end - begin) + " ms.");
    }

    /**
     * 1.Random()构造一个随机数生成器，种子是与nanoTime异或后的值。每遍输出的多个序列均不同。随机性更强。
     * 2 Random(long seed)用种子seed构造一个随机数生成器，种子是给定的。每遍输出的多个序列均相同。
     * 3.种子就是生成随机数的根，就是产生随机数的基础。计算机的随机数都是伪随机数，以一个真随机数（种子）作为初始条件，然后用一定的算法不停迭代产生随机数。
     * 4.Java项目中通常是通过Math.random方法和Random类来获得随机数。
     * 5.Random类中不含参构造方法每次都使用当前时间作为种子，而含参构造方法是以一个固定值作为种子。
     */
    static Random random = new Random(0);

    static long random() {
//        generate a random_number from 0 to 9999
        return random.nextInt(10000);
    }
}
