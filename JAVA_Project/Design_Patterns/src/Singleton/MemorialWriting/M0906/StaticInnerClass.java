package Singleton.MemorialWriting.M0906;

import java.util.concurrent.atomic.AtomicLong;

public class StaticInnerClass {
    private AtomicLong id = new AtomicLong(6);
    private static class SingleHolder {
        private static StaticInnerClass staticInnerClass = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return SingleHolder.staticInnerClass;
    }

    public long getId() {
        return this.id.incrementAndGet();
    }
}
