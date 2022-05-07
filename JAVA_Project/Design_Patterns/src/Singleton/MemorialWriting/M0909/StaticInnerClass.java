package Singleton.MemorialWriting.M0909;

import java.util.concurrent.atomic.AtomicLong;

public class StaticInnerClass {
    private AtomicLong id = new AtomicLong(3);

    private StaticInnerClass() {

    }

    private static class SingletonHolder {
        private static StaticInnerClass instance = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return this.id.incrementAndGet();
    }
}
