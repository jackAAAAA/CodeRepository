package Observer.MemorialWriting_Observer.M0901;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread {
    MyObserver observer;
    String content;
    CountDownLatch latch;

    MessageSend(MyObserver observer, String content, CountDownLatch latch) {
        this.observer = observer;
        this.content = content;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            this.observer.callMe(this.content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            System.out.println(this.getName() + "子线程消息发送完毕！");
            System.out.println();
        }
    }
}
