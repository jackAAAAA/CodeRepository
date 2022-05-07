package Observer.MemorialWriting_Observer.M0903;

import java.util.concurrent.CountDownLatch;

public class MessageSend extends Thread{

    private MyObserver observer;
    private String content;
    private CountDownLatch latch;

    public MessageSend(MyObserver observer, String content, CountDownLatch latch) {
        this.observer = observer;
        this.content = content;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            observer.callMe(this.content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            System.out.println(this.getName() + "子线程易发送完毕消息啦");
        }
    }
}
