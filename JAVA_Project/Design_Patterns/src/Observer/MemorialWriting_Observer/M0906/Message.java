package Observer.MemorialWriting_Observer.M0906;

import java.util.concurrent.CountDownLatch;

public class Message extends Thread {

    private CountDownLatch latch;
    private String content;
    private MyObserver observer;

    public Message(CountDownLatch latch, String content, MyObserver observer) {
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
            System.out.println(this.getName() + "子线程工作完成！");
            latch.countDown();
        }
    }
}
