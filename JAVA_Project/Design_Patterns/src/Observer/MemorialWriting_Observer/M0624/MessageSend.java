package Observer.MemorialWriting_Observer.M0624;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread{
    CountDownLatch latch;
    String content;
    MyObserver observer;

    public MessageSend(CountDownLatch latch, String content, MyObserver observer) {
        this.latch = latch;
        this.content = content;
        this.observer = observer;
    }

    @Override
    public void run() {
        try {
            observer.CallMe(this.content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
//            System.out.println(this.getName() + "结束！");
        }
    }
}
