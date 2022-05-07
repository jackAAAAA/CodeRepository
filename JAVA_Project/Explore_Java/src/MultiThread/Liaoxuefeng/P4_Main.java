package MultiThread.Liaoxuefeng;

import java.time.LocalTime;

/**
 * Daemon_Thread: 并未掌握实践的要领！
 */
public class P4_Main {
    static class TimerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println(LocalTime.now());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TimerThread t = new TimerThread();
        t.setDaemon(true);
        t.start();
//        System.out.println("main running!");
    }
}
