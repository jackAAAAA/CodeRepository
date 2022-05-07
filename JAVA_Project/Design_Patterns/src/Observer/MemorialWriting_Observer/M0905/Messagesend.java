package Observer.MemorialWriting_Observer.M0905;

import java.util.concurrent.CountDownLatch;

public class Messagesend extends Thread {
    private CountDownLatch latch;
    private String content;
    private MyObserver observer;

    public Messagesend(MyObserver observer, CountDownLatch latch, String content) {
        this.observer = observer;
        this.latch = latch;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            observer.callMe(this.content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            System.out.println(this.getName() + "子线程已发送完毕！");
        }
    }
}
