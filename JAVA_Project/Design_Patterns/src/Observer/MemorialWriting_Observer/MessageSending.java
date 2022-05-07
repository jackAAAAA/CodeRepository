package Observer.MemorialWriting_Observer;

import java.util.concurrent.CountDownLatch;

public class MessageSending extends Thread {
    CountDownLatch latch;
    MyObserver observer;
    String content;

    public MessageSending(CountDownLatch latch, MyObserver observer, String content) {
        this.latch = latch;
        this.observer = observer;
        this.content = content;
    }

    @Override
    public void run() {
//        long beginTime = System.currentTimeMillis();
        try {
            observer.callMe(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            long endTime = System.currentTimeMillis();
//            System.out.println(this.getName() + "消息发送完毕，耗时：" + (endTime - beginTime));
//            System.out.println();
            latch.countDown();
        }
    }
}
