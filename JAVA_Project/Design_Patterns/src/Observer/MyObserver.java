package Observer;

/**
 * 观察者
 */
public interface MyObserver {
    /**
     * 找我 * @param content 找我啥事
     */
    void callMe(String content);

    /**
     * 观察者名字
     */
    String getName();
}
