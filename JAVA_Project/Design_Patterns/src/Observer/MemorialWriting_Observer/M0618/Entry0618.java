package Observer.MemorialWriting_Observer.M0618;

public class Entry0618 {

    public static final String NAME = "老板";

    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter(NAME);
        TheOppressed theOppressed1 = new TheOppressed("打工仔1");
        TheOppressed theOppressed2 = new TheOppressed("打工仔2");
        TheOppressed theOppressed3 = new TheOppressed("打工仔3");
        exploiter.registerObserver(theOppressed1);
        exploiter.registerObserver(theOppressed2);
        exploiter.registerObserver(theOppressed3);
        exploiter.postMessage("这个月加房租500！！！");
        exploiter.postMessage("快来交房租！！！");
    }
}
