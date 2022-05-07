package MultiThread.Liaoxuefeng;

import java.util.concurrent.locks.StampedLock;

public class P12_Main {
    static class Point {
        private final StampedLock stampedLock = new StampedLock();
        private double x, y;

        public void move(double deltaX, double deltaY) {
            long stamp = stampedLock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        }

        public double distanceFromOrigin() {
            long stamp = stampedLock.tryOptimisticRead();
            double currentX = x;
            double currentY = y;
            if (!stampedLock.validate(stamp)) {
                stamp = stampedLock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }
    }

    public static void main(String[] args) throws Exception {
        Point point = new Point();
        Thread rt1 = new Thread(() -> {
            System.out.println("Initial distance: " + point.distanceFromOrigin());
        });
        rt1.start();
        rt1.join();
        Thread wt = new Thread(() -> {
            point.move(3, 4);
        });
//        可利用lambda表达式将线程需要执行的任务（即函数）直接放在线程里或者单独列出
//        Thread rt2 = new Thread(() -> {
//            System.out.println("Moved distance: " + point.distanceFromOrigin());
//        });
//        Thread rt2 = new Thread(() -> P12_Main.print_test());
        Thread rt2 = new Thread(P12_Main::print_test);
        wt.start();
        rt2.start();
        wt.join();
        rt2.join();
    }

    static void print_test() {
        System.out.println("Moved distance: ");
    }
}
