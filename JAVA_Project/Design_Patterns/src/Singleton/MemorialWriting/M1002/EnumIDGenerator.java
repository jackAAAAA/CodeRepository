package Singleton.MemorialWriting.M1002;

import java.util.concurrent.atomic.AtomicLong;

public enum EnumIDGenerator {
    INSTANCE;
    private AtomicLong id = new AtomicLong(3);

    public long getID() {
        return INSTANCE.id.incrementAndGet();
    }
}
