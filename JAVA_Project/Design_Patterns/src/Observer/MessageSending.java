package Observer;


import java.util.Observer;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 *
 * 描述：消息发送
 *
 */
public class MessageSending extends Thread {
    private CountDownLatch latch;
    private MyObserver observer;
    private String content;
    private int num;

    public MessageSending(CountDownLatch latch, MyObserver observer, String content, int num) {
        this.latch = latch;
        this.observer = observer;
        this.content = content;
        this.num = num;
    }

    /**
     * 消息通知
     */
    @Override
    public void run(){
        int time = 0;
        try {
//            Random random = new Random(num+7);
//            time = random.nextInt(6)*1000;
//            sleep(time);
            observer.callMe(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            System.out.println(this.getName()+"发送消息完毕,耗时："+time+"毫秒");
            System.out.println();
//            计数器(state)减1
            latch.countDown();
        }
    }

}
