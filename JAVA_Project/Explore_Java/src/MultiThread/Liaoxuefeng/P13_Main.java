package MultiThread.Liaoxuefeng;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class P13_Main {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> map = new  ConcurrentHashMap<>() {{
            put("A", 1);
            put("B", 2);
            put("C", 3);
        }};
        Thread t1 = new Thread(() -> {
            System.out.println("初始时，A/B/C各自的值：");
            System.out.println("获取A的value: " + map.get("A"));
            System.out.println("获取B的value: " + map.get("B"));
            System.out.println("获取C的value: " + map.get("C"));
        });
        t1.start();
        t1.join();
        Thread t2 = new Thread(() -> {
            map.put("A", 4);
            map.put("B", 5);
            map.put("C", 6);
        });
        t2.start();
        t2.join();
        Thread t3 = new Thread(() -> {
            System.out.println("修改后，A/B/C各自的值：");
            System.out.println("获取A的value: " + map.get("A"));
            System.out.println("获取B的value: " + map.get("B"));
            System.out.println("获取C的value: " + map.get("C"));
        });
        t3.start();
        t3.join();
    }
}
