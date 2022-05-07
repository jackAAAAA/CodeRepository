package Observer.MemorialWriting_Observer.M1002;

public class Entry_1002 {
    public static void main(String[] args) {
        TheExploiter theExploiter = new TheExploiter("房东");
        TheOppressed theOppressed1 = new TheOppressed("打工仔1");
        TheOppressed theOppressed2 = new TheOppressed("打工仔2");
        TheOppressed theOppressed3 = new TheOppressed("打工仔3");
        theExploiter.registerObserver(theOppressed1);
        theExploiter.registerObserver(theOppressed2);
        theExploiter.registerObserver(theOppressed3);
        theExploiter.postMessage("这个月加房租600！");
        theExploiter.postMessage("快来交房租！");
    }
}
