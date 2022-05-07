package Observer.MemorialWriting_Observer.M0903;

public interface MySubject {
    void registerObserver(MyObserver observer);

    void removeObserver(MyObserver observer);

    void notifyObserver();
}
