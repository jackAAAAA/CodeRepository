package MultiThread.Liaoxuefeng;

/**
 * 同步方法
 */
public class P6_Main {
    static class Counter {
        private int count = 0;

        public void add(int n) {
            synchronized (this) {
                count += n;
            }
        }

//        public void dec(int n) {
//            synchronized (this) {
//                count -= n;
//            }
//        }
        public synchronized void dec(int n) {
            count -= n;
        }

        public int get() {
            return count;
        }
    }

    public static void main(String[] args) throws Exception {
        var c1 = new Counter();
        var c2 = new Counter();
        Thread t1 = new Thread(() -> c1.add(100));
        Thread t2 = new Thread(() -> c1.dec(10));
        Thread t3 = new Thread(() -> c2.add(100));
        Thread t4 = new Thread(() -> c2.dec(1000));
        t1.start(); t2.start();
        t3.start(); t4.start();
        t1.join(); t2.join();
        t3.join(); t4.join();
        System.out.println("c1: " + c1.get());
        System.out.println("c2: " + c2.get());
    }
}
