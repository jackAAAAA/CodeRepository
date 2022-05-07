package Singleton.MemorialWriting.M0909;

import java.util.concurrent.atomic.AtomicLong;

public enum EnumId {
    INSTANCES;
    private AtomicLong id = new AtomicLong(6);

    public long getID() {
        return this.id.incrementAndGet();
    }
}
