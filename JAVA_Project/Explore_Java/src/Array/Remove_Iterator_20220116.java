package Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Remove_Iterator_20220116 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(5);
        list.add(10);
        list.add(40);
        list.add(100);
        list.add(1000);
        list.add(200);
        list.add(300);
        list.add(5000);
        list.add(10000);

        Iterator<Integer> it = list.iterator();
//        当通过迭代器删除集合中的元素之后，集合变小，因此应该在初始时就统计集合的大小
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            int temp = it.next();
            if (temp < 10000) {
                it.remove();
            }
        }
        list.forEach(System.out::println);
    }
}
