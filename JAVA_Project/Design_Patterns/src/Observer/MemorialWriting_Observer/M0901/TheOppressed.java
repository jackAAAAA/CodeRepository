package Observer.MemorialWriting_Observer.M0901;

public class TheOppressed implements MyObserver{
    private String name;

    TheOppressed(String name) {
        this.name = name;
    }

    @Override
    public void callMe(String content) {
        System.out.println("已收到消息！内容是：" + content);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
