package Singleton.MemorialWriting.M0902;

import java.util.concurrent.atomic.AtomicLong;

public class staticClass {
    private static class singletonHolder {
        private static final staticClass instance = new staticClass();
    }

    private AtomicLong id = new AtomicLong(6);

    public static staticClass getInstance() {
        return singletonHolder.instance;
    }

    public long getID() {
        return this.id.incrementAndGet();
    }
}
