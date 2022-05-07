package Observer.MemorialWriting_Observer.M0621;

public interface MySubject {
    void registerObserver(MyObserver observer);

    void removeObserver(MyObserver observer);

    void notifyObserver();
}
