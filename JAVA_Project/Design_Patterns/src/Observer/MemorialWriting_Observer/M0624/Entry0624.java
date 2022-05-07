package Observer.MemorialWriting_Observer.M0624;

public class Entry0624 {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("房东");
        TheOppressed oppressed1 = new TheOppressed("打工仔1");
        TheOppressed oppressed2 = new TheOppressed("打工仔2");
        TheOppressed oppressed3 = new TheOppressed("打工仔3");
        exploiter.registerObserver(oppressed1);
        exploiter.registerObserver(oppressed2);
        exploiter.registerObserver(oppressed3);
        exploiter.postMessage("这个月临时加租500！");
        exploiter.postMessage("打工仔快来交房租！！！");
    }
}
