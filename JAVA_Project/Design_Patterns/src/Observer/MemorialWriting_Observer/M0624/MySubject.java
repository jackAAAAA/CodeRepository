package Observer.MemorialWriting_Observer.M0624;

public interface MySubject {
    void registerObserver(MyObserver observer);

    void removeObserver(MyObserver observer);

    void notifyObserver();
}
