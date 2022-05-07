package Observer;

/**
 * 被观察者
 */
public interface MySubject {
    /**
     * 观察者注册
     */
    void registerObserver(MyObserver observer);

    /**
     * 删除观察者
     */
    void removeObserver(MyObserver observer);

    /**
     * 主题有变化时通知观察者
     */
    void notifyObserver();
}
