package handler;

/*
 * 项目名:    Demo
 * 包名       handler
 * 文件名:    Handler
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:10
 * 描述:     TODO
 */
public class Handler {
    private Looper mLooper;
    private MessageQueue mQueue;

    public Handler() {
        //获取当前线程的looper
        mLooper = Looper.myLooper();
        //获取当前线程的消息列队
        mQueue = mLooper.messageQuene;
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(Message message) {
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    /**
     * 处理消息
     * @param message
     */
    public void handleMessage(Message message) {

    }

    /**
     * 分发消息
     * @param message
     */
    public void dispatchMessage(Message message) {
        handleMessage(message);
    }
}
