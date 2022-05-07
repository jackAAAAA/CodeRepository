package Observer.MemorialWriting_Observer.M0902;

public class Test_0902 {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("房东");
        TheOppressed theOppressed1 = new TheOppressed("打工人1");
        TheOppressed theOppressed2 = new TheOppressed("打工人2");
        exploiter.registerObserver(theOppressed1);
        exploiter.registerObserver(theOppressed2);
        exploiter.postMessage("房租加500！");
        exploiter.postMessage("快来交房租！");
    }
}
