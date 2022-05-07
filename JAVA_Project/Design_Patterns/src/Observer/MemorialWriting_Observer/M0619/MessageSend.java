package Observer.MemorialWriting_Observer.M0619;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread {
    CountDownLatch latch;
    MyObserver observer;
    String content;

    public MessageSend(CountDownLatch latch, MyObserver observer, String content) {
        this.latch = latch;
        this.observer = observer;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            observer.CallMe(this.content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}
