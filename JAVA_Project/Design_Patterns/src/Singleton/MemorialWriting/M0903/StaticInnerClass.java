package Singleton.MemorialWriting.M0903;

import java.util.concurrent.atomic.AtomicLong;

public class StaticInnerClass {

    private StaticInnerClass() {

    }

    private static class singletonHolder {
        private static final StaticInnerClass instance = new StaticInnerClass();
    }

    private AtomicLong id = new AtomicLong(5);

    public static StaticInnerClass getInstance() {
        return singletonHolder.instance;
    }

    public Long getID() {
        return id.incrementAndGet();
    }
}
