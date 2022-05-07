package Observer.MemorialWriting_Observer.M0909;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread{

    private CountDownLatch latch;
    private MyObserver observer;
    private String content;

    public MessageSend(CountDownLatch latch, MyObserver observer, String content) {
        this.latch = latch;
        this.observer = observer;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            observer.callMe(this.content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(this.getName() + "子线程已发送消息！");
            latch.countDown();
        }
    }
}
