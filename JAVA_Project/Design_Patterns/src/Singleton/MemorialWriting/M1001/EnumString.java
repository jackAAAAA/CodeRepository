package Singleton.MemorialWriting.M1001;

public enum EnumString {
    INSTANCE;

    private String name = "world!";

    public String getName() {
        return INSTANCE.name;
    }

    public void setName(String name) {
        INSTANCE.name = name;
    }
}
