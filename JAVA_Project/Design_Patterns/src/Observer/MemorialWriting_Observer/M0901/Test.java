package Observer.MemorialWriting_Observer.M0901;

public class Test {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("房东");
        TheOppressed theOppressed1 = new TheOppressed("打工人1");
        TheOppressed theOppressed2 = new TheOppressed("打工人2");
        TheOppressed theOppressed3 = new TheOppressed("打工人3");
        TheOppressed theOppressed4 = new TheOppressed("打工人4");
        exploiter.registerObserver(theOppressed1);
        exploiter.registerObserver(theOppressed2);
        exploiter.registerObserver(theOppressed3);
        exploiter.registerObserver(theOppressed4);
        exploiter.postMessage("打工人，这个月房租加200！");
        exploiter.postMessage("打工人，快来交房租！");
    }
}
