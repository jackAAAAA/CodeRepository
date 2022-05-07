package Singleton;

public enum EnumString {
    INSTANCE;
    private String name = "world!";

    public String getName() {
        return INSTANCE.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
