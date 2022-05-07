package Singleton.MemorialWriting.M0905;

import java.util.concurrent.atomic.AtomicLong;

public enum EnumId {
    INSTANCE;
    private AtomicLong id = new AtomicLong(5);

    public long getID() {
        return this.id.incrementAndGet();
    }
}
