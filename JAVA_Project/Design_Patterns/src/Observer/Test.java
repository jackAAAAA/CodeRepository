package Observer;

public class Test {
    public static void main(String[] args) {
        Exploiter exploiter = new Exploiter("房东");
        TheOppressed theOppressed1 = new TheOppressed("打工仔1");
        TheOppressed theOppressed2 = new TheOppressed("打工仔2");
        TheOppressed theOppressed3 = new TheOppressed("打工仔3");
        exploiter.registerObserver(theOppressed1);
        exploiter.registerObserver(theOppressed2);
        exploiter.registerObserver(theOppressed3);
        exploiter.postMessage("这个月房租加200！！！");
        exploiter.postMessage("打工仔们快来交房租啦~~");
    }
}
