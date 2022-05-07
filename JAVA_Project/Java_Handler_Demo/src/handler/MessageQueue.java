package handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 项目名:    Demo
 * 包名       Looper
 * 文件名:    MessageQueue
 * 创建者:    ZJB
 * 创建时间:  2018/1/6 on 23:10
 * 描述:     TODO
 */
public class MessageQueue {

    Message[] mItems;
    int mPutIndex;
    //队列中消息数
    private int mCount;
    private int mTakeIndex;
    //锁
    Lock mLock;
    //唤醒，沉睡某个线程操作
    Condition getCondition;//可取
    Condition addCondition;//可添加


    public MessageQueue() {
        mItems = new Message[50];
        mLock = new ReentrantLock();
        getCondition = mLock.newCondition();
        addCondition = mLock.newCondition();
    }

    /**
     * 消息队列取消息 出队
     *
     * @return
     */
    Message next() {
        Message msg = null;
        try {
            mLock.lock();
            //检查队列是否空了
            while (mCount <= 0) {
                //阻塞
                System.out.println("MessageQueue：" + "队列空了，读锁阻塞");
                getCondition.await();
            }
            msg = mItems[mTakeIndex];//可能空
            //消息被处理后，置空数组中该项
            mItems[mTakeIndex] = null;
            //处理越界，index大于数组容量时，取第一个item
            mTakeIndex = (++mTakeIndex >= mItems.length) ? 0 : mTakeIndex;
            mCount--;
            //通知生产者生产
            addCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }

        return msg;
    }

    /**
     * 添加消息进队列
     *
     * @param message
     */

    public void enqueueMessage(Message message) {

        try {
            mLock.lock();
            //检查队列是否满了
            while (mCount >= mItems.length) {
                //阻塞
                System.out.println("MessageQueue：" + "队列空了，写锁阻塞");
                addCondition.await();
            }

            mItems[mPutIndex] = message;
            //处理越界，index大于数组容量时，替换第一个item
            mPutIndex = (++mPutIndex >= mItems.length) ? 0 : mPutIndex;
            mCount++;
            //通知消费者消费
            getCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
    }
}
