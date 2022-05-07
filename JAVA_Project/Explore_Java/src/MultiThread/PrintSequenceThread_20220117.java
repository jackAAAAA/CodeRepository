package MultiThread;

public class PrintSequenceThread_20220117 extends Thread {

    private static final Object lock = new Object();

    private static int current = 0;

    private final int threadNo, threadCount, max;

    public PrintSequenceThread_20220117(int threadNo, int threadCount, int max) {
        this.threadNo = threadNo;
        this.threadCount = threadCount;
        this.max = max;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (current % threadCount != threadNo) {
                    if (current > max) {
                        break;
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (current > max) {
                    break;
                }
                System.out.println("thread-" + threadNo + ": " + current);
                ++current;
                lock.notifyAll();
            }
        }
    }


    public static void main(String[] args) {
        int threadCount = 3, max = 100;
        for (int i = 0; i < threadCount; i++) {
            new PrintSequenceThread_20220117(i, threadCount, max).start();
        }
    }

}
