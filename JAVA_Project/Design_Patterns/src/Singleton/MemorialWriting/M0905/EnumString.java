package Singleton.MemorialWriting.M0905;

public enum EnumString {
    INSTANCE;
    private String name = "Hello word!";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
