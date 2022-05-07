package Singleton.MemorialWriting.M1001;

import java.util.concurrent.atomic.AtomicLong;

public enum EnumIdGenerator {
    INSTANCE;

    private AtomicLong id = new AtomicLong(3);

    public long getId() {
        return id.incrementAndGet();
    }

}
