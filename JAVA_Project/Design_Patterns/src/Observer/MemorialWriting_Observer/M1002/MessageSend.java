package Observer.MemorialWriting_Observer.M1002;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread{
    private MyObserver observer;
    private CountDownLatch latch;
    private String content;

    public MessageSend(MyObserver observer, CountDownLatch latch, String content) {
        this.observer = observer;
        this.latch = latch;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            observer.CallMe(this.content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(this.getName() + "子线程发送完毕！");
            latch.countDown();
        }
    }
}
