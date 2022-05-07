package Observer.MemorialWriting_Observer.M0903;

public class Test_0903 {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("商家");
        TheOppressed theOppressed1 = new TheOppressed("顾客1");
        TheOppressed theOppressed2 = new TheOppressed("顾客2");
        TheOppressed theOppressed3 = new TheOppressed("顾客3");
        exploiter.registerObserver(theOppressed1);
        exploiter.registerObserver(theOppressed2);
        exploiter.registerObserver(theOppressed3);
        exploiter.postMessage("货物涨价啦，涨价300！");
        exploiter.postMessage("快来购买哟！");
    }
}
