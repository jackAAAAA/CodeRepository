package handler;

/*
 * 项目名:    Demo
 * 包名       Looper
 * 文件名:    Handler
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:10
 * 描述:     TODO
 */
public class Looper {
    final MessageQueue messageQuene;
    private static ThreadLocal<Looper> threadLocal = new ThreadLocal<>();

    public Looper() {
        messageQuene = new MessageQueue();
    }

    /**
     * 为当前线程初始化一个looper副本对象
     */
    public static void prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        threadLocal.set(new Looper());
        System.out.println("looper初始化");
    }

    /**
     * 获取当前线程的looper副本对象
     *
     * @return
     */
    public static Looper myLooper() {
        return threadLocal.get();
    }

    /**
     * 轮询消息
     */
    public static void loop() {
        //获取当前线程的looper对象
        Looper me = myLooper();
        Message msg;
        //开始轮询消息
        for (; ; ) {
            //轮询消息，没有消息就阻塞
            msg = me.messageQuene.next();
            if (msg == null || msg.target == null) {
                System.out.println("Looper：" + "空消息");
                continue;
            }
            System.out.println("Looper：" + "looper轮询到了消息，发送消息");
            //轮询到了消息分发消息
            msg.target.dispatchMessage(msg);

        }
    }
}
