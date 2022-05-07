package Observer.MemorialWriting_Observer.M0909;

public class Test_0909 {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("老板");
        TheOppressed theOppressed1 = new TheOppressed("打工人1");
        TheOppressed theOppressed2 = new TheOppressed("打工人2");
        TheOppressed theOppressed3 = new TheOppressed("打工人3");
        exploiter.registerObserver(theOppressed1);
        exploiter.registerObserver(theOppressed2);
        exploiter.registerObserver(theOppressed3);
        exploiter.postMessage("这个月加房租600！");
        exploiter.postMessage("快来交房租！");
    }
}
