package MultiThread;

public class PrintSequenceThread_20220118 implements Runnable {
    private static final Object lock = new Object();

    private static int current = 0;

    private final int threadNo, threadCount, max;

    PrintSequenceThread_20220118(int threadNo, int threadCount, int max) {
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
                System.out.println("threadNo-" + threadNo + ": " + current++);
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 3, max = 101;
        for (int i = 0; i < threadCount; i++) {
            new Thread(new PrintSequenceThread_20220118(i, threadCount, max)).start();
        }
    }
}
