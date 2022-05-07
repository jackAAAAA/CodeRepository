package MultiThread.Liaoxuefeng;

public class P2_Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("thread starts!"));
        System.out.println("main starts!");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main ends!");
    }
}
