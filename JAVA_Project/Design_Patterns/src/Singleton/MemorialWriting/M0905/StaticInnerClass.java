package Singleton.MemorialWriting.M0905;

import java.util.concurrent.atomic.AtomicLong;

public class StaticInnerClass {
    private AtomicLong id = new AtomicLong(6);

    private StaticInnerClass() {

    }

    private static class SingletonHolder {
        private static StaticInnerClass instance = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return SingletonHolder.instance;
    }

    public long getID() {
        return this.id.incrementAndGet();
    }
}
