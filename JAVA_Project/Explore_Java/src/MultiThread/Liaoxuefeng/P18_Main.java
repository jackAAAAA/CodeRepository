package MultiThread.Liaoxuefeng;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal表示线程的“局部变量”，它确保每个线程的ThreadLocal变量都是各自独立的
 */
public class P18_Main {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(3);
        String[] users = new String[] { "Bob", "Alice", "Tim", "Mike", "Lily", "Jack", "Bush" };
        for (String user : users) {
            es.submit(new Task(user));
        }
//        awaitTermination：等待一个指定时间再将线程池关闭，不过实际运行起来，常常不能在到达指定时间后而关闭线程池
//        因此其在关闭线程池要结合shutdown()一起使用
        System.out.println("AwaitTermination: " + es.awaitTermination(3, TimeUnit.SECONDS));
        es.shutdown();
    }

    static class UserContext implements AutoCloseable {
        private static final ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

        public UserContext(String name) {
            userThreadLocal.set(name);
            System.out.printf("[%s] init user %s...\n", Thread.currentThread().getName(), UserContext.getCurrentUser());
        }

        public static String getCurrentUser() {
            return userThreadLocal.get();
        }

        @Override
        public void close() {
            System.out.printf("[%s] cleanup for user %s...\n", Thread.currentThread().getName(),
                    UserContext.getCurrentUser());
            userThreadLocal.remove();
        }
    }

    static class Task implements Runnable {

        final String username;

        public Task(String username) {
            this.username = username;
        }

        @Override
        public void run() {
            try (var ctx = new UserContext(this.username)) {
                new Task1().process();
                new Task2().process();
                new Task3().process();
            }
        }
    }

    static class Task1 {
        public void process() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            System.out.printf("[%s] check user %s...\n", Thread.currentThread().getName(), UserContext.getCurrentUser());
        }
    }

    static class Task2 {
        public void process() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            System.out.printf("[%s] %s registered ok.\n", Thread.currentThread().getName(), UserContext.getCurrentUser());
        }
    }

    static class Task3 {
        public void process() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            System.out.printf("[%s] work of %s has done.\n", Thread.currentThread().getName(),
                    UserContext.getCurrentUser());
        }
    }
}
