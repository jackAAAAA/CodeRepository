package Singleton.MemorialWriting.M0906;

public enum EnumString {
    INSTANCE;
    private String name = "Hello Word!";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
