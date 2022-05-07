package Singleton.MemorialWriting.M1002;

import java.util.concurrent.atomic.AtomicInteger;

public class StaticInnerClass {
    private AtomicInteger id = new AtomicInteger(5);

    private StaticInnerClass() {

    }

    private static class singletonHolder{
        private static final StaticInnerClass instance = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return singletonHolder.instance;
    }

    public int getId() {
        return id.incrementAndGet();
    }
}
