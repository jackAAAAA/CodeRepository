package Observer.MemorialWriting_Observer.M1001;

public class TheOppressed implements MyObserver{
    private String name;

    public TheOppressed(String name) {
        this.name = name;
    }

    @Override
    public void CallMe(String message) {
        System.out.println(this.name + "收到消息，为：" + message);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
