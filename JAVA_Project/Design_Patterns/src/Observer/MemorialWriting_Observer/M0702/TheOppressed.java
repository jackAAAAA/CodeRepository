package Observer.MemorialWriting_Observer.M0702;

public class TheOppressed implements MyObserver {
    String name;

    TheOppressed(String name) {
        this.name = name;
    }

    @Override
    public void CallMe(String content) {
        System.out.println(this.name + "收到消息，内容是：" + content);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
