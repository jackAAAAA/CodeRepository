package Observer.MemorialWriting_Observer.M0905;

public class Test_0905 {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("Tencent");
        TheOppressed worker1 = new TheOppressed("jack1");
        TheOppressed worker2 = new TheOppressed("jack2");
        TheOppressed worker3 = new TheOppressed("jack3");
        exploiter.registerObserver(worker1);
        exploiter.registerObserver(worker2);
        exploiter.registerObserver(worker3);
        exploiter.postMessage("明天准备面试！");
        exploiter.postMessage("开始面试！");
    }
}
