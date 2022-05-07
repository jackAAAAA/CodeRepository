package Singleton.MemorialWriting.M0909;

public enum EnumString {
    INSTANCE;
    private String name = "Hello world!";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
