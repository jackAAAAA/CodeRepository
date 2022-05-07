package Observer.MemorialWriting_Observer;

public class TheOppressed implements MyObserver{
    String name;

    public TheOppressed(String name) {
        this.name = name;
    }
    @Override
    public void callMe(String content) {
        System.out.println(name + " " + content);
    }

    @Override
    public String getName() {
        return name;
    }
}
