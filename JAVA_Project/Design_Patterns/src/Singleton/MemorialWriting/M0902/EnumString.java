package Singleton.MemorialWriting.M0902;

public enum EnumString {
    INSTANCE;
    private String name = "word!!!!";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
