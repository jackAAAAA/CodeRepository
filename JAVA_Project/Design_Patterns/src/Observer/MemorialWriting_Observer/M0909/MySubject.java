package Observer.MemorialWriting_Observer.M0909;

public interface MySubject {
    void registerObserver(MyObserver observer);

    void removeObserver(MyObserver observer);

    void notifyObserver();
}
