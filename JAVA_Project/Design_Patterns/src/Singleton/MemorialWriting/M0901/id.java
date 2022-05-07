package Singleton.MemorialWriting.M0901;

import java.util.concurrent.atomic.AtomicLong;

public enum id {
    INSTANCE;
    private AtomicLong ID = new AtomicLong(10);

    public long getID() {
        return ID.incrementAndGet();
    }
}
