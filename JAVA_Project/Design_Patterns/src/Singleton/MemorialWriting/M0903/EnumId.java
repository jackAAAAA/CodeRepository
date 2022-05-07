package Singleton.MemorialWriting.M0903;

import java.util.concurrent.atomic.AtomicLong;

public enum EnumId {
    INSTANCE;
    private AtomicLong id = new AtomicLong(3);

    public long getID() {
        return this.id.incrementAndGet();
    }
}
