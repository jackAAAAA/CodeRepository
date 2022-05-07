package Singleton.MemorialWriting.M1002;

public enum EnumString {
    INSTANCE;
    private String name = "1002";

    public String getName() {
        return INSTANCE.name;
    }

    public void setName(String name) {
        INSTANCE.name = name;
    }
}
