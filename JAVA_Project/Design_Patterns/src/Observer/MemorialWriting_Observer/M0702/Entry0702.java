package Observer.MemorialWriting_Observer.M0702;

public class Entry0702 {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("房东");
        TheOppressed theoppressed1 = new TheOppressed("打工仔1");
        TheOppressed theoppressed2 = new TheOppressed("打工仔2");
        TheOppressed theoppressed3 = new TheOppressed("打工仔3");
        exploiter.registerObserver(theoppressed1);
        exploiter.registerObserver(theoppressed2);
        exploiter.registerObserver(theoppressed3);
        exploiter.postMessage("这个月房租加600！！");
        exploiter.postMessage("快来交房租！！！");
    }
}
