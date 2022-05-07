package Observer.MemorialWriting_Observer.M0906;

public class TheOppressed implements MyObserver {

    private String name;

    public TheOppressed(String name) {
        this.name = name;
    }

    @Override
    public void callMe(String content) {
        System.out.println(this.name + "收到消息啦！消息为：" + content);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
