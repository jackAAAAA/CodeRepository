package Observer.MemorialWriting_Observer.M0702;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread {
    CountDownLatch latch;
    String content;
    MyObserver observer;

    MessageSend(MyObserver observer, CountDownLatch latch, String content) {
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
            System.out.println(this.getName() + "消息已发送！！！");
            latch.countDown();
        }
    }
}
