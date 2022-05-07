package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 项目名:    Demo
 * 包名       thread
 * 文件名:    ConditionTest
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:19
 * 描述:     TODO
 */
public class ConditionTest {
    /**
     * @param args
     */
    public static void main(String[] args) {

        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 4; i++) {
                    business.sub2(i);
                }
            }
        }
        ).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 4; i++) {
                    business.sub3(i);
                }
            }
        }
        ).start();
        for (int i = 1; i <= 4; i++) {
            business.main(i);
        }

    }


    static class Business {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        private int shouldSub = 1;

        public void sub2(int i) {
            lock.lock();
            try {
                while (shouldSub != 2) {
                    try {
                        condition2.await();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                System.out.println("sub2 thread ,loop of " + i);
                shouldSub = 3;
                condition3.signal();
            } finally {
                lock.unlock();
            }
        }


        public void sub3(int i) {
            lock.lock();
            try {
                while (shouldSub != 3) {
                    try {
                        condition3.await();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                System.out.println("sub3 thread ,loop of " + i);

                shouldSub = 1;
                condition1.signal();
            } finally {
                lock.unlock();
            }
        }

        public void main(int i) {
            lock.lock();
            try {
                while (shouldSub != 1) {
                    try {
                        condition1.await();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                System.out.println("main thread ,loop of " + i);

                shouldSub = 2;
                condition2.signal();
            } finally {
                lock.unlock();
            }
        }

    }
}
