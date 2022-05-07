package Singleton.MemorialWriting.M0906;

import java.util.concurrent.atomic.AtomicLong;

public enum EnumId {
    INSTANCE;
    private AtomicLong id = new AtomicLong(8);

    public long getID() {
        return id.incrementAndGet();
    }
}
