package Observer.MemorialWriting_Observer.M0905;

public class TheOppressed implements MyObserver {

    private String name;

    public TheOppressed(String name) {
        this.name = name;
    }

    @Override
    public void callMe(String content) {
        System.out.println(this.name + "已收到消息！内容是：" + content);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
