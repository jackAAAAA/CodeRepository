package Observer.MemorialWriting_Observer.M0618;

public class TheOppressed implements MyObserver{
    String name;

    public TheOppressed(String name) {
        this.name = name;
    }

    @Override
    public void callMe(String content) {
        System.out.println(this.name + "：" + content);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
