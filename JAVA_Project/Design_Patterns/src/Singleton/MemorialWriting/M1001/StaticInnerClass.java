package Singleton.MemorialWriting.M1001;

import java.util.concurrent.atomic.AtomicInteger;

public class StaticInnerClass {
    private AtomicInteger id = new AtomicInteger(0);

    private StaticInnerClass() {

    }

    private static class SingletonHolder {
        private static final StaticInnerClass instance = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return SingletonHolder.instance;
    }

    public int getID() {
        return id.incrementAndGet();
    }
}
