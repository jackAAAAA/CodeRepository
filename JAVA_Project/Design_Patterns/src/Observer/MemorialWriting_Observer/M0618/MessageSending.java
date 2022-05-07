package Observer.MemorialWriting_Observer.M0618;

import java.util.concurrent.CountDownLatch;

public class MessageSending extends Thread {
    CountDownLatch latch;
    MyObserver observer;
    String content;

    public MessageSending(CountDownLatch latch, String content, MyObserver observer) {
        this.latch = latch;
        this.content = content;
        this.observer = observer;
    }

    @Override
    public void run() {
        try {
            observer.callMe(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

}
