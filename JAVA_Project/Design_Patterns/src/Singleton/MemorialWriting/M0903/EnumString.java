package Singleton.MemorialWriting.M0903;

public enum EnumString {
    INSTANCE;
    private String name = "Hello!!!";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
