package Singleton;

import java.util.concurrent.atomic.AtomicLong;

public class StaticInnerClass {
    private AtomicLong id = new AtomicLong(0);

    private StaticInnerClass() {
    }

    private static class SingletonHolder {
        private static final StaticInnerClass instance = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}

