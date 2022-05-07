package Queue.PriorityQueue;

import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class NC97 {
    /**
     * return topK string
     */
    public static void main(String[] args) {
        String[] strings = {"9", "8", "66", "555", "111"};
        int k = 3;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String str : strings) {
            map.put(str, map.getOrDefault(str, 0) + 1);
        }
//         PriorityQueue<String> q = new PriorityQueue<String>((String e1, String e2) -> (map.get(e1) == map.get(e2) ? e1.compareTo(e2) : map.get(e1) - map.get(e2)));
//         PriorityQueue<String> q = new PriorityQueue<String>(new Comparator<String>(){
//             public int compare(String e1, String e2) {
//                 return map.get(e1) == map.get(e2) ? e1.compareTo(e2) : map.get(e1) - map.get(e2);
//             }
//         });
        PriorityQueue<Map.Entry<String, Integer>> q = new PriorityQueue<Map.Entry<String, Integer>>(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e1.getValue().equals(e2.getValue()) ? e2.getKey().compareTo(e1.getKey()) : e1.getValue() - e2.getValue();
            }
        });
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//             if (q.size() < k) {
//                 q.offer(entry);
//             } else if (map.get(entry.getKey()) > map.get(q.peek().getKey())) {
//                 q.poll();
//                 q.offer(entry);
//             }
            q.offer(entry);
            if (q.size() > k) {
                q.poll();
            }
        }
//        String[][] res = new String[k][2];
//        for (int i = k - 1; i >= 0; --i) {
//            Map.Entry<String, Integer> entry = q.poll();
//            res[i][0] = entry.getKey();
//            res[i][1] = String.valueOf(map.get(entry.getKey()));
//        }
        while (!q.isEmpty()) {
            Map.Entry<String, Integer> entry = q.poll();
            System.out.println("Key: " + entry.getKey() + " " + "Value: " + entry.getValue());
        }
//        for (String[] r : res) {
//            System.out.println(r[0] + " " + r[1]);
//        }
    }
}
