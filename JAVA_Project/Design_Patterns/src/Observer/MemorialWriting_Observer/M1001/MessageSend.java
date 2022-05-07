package Observer.MemorialWriting_Observer.M1001;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread{
    private MyObserver observer;
    private String message;
    private CountDownLatch latch;

    public MessageSend(MyObserver observer, String message, CountDownLatch latch) {
        this.observer = observer;
        this.message = message;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            this.observer.CallMe(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(this.getName() + "子线程，已将消息发送完毕！");
            this.latch.countDown();
        }
    }
}
