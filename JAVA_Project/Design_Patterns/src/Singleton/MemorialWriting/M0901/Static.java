package Singleton.MemorialWriting.M0901;

import java.util.concurrent.atomic.AtomicLong;

public class Static {
    private AtomicLong id = new AtomicLong(9);

    private Static() {

    }

    private static class SingletonHolder {
        private final static Static instance = new Static();
    }

    public static Static getInstance() {
        return SingletonHolder.instance;
    }

    public long getId() {
        return id.incrementAndGet();
    }
}
