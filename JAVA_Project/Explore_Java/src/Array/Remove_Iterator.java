package Array;

import java.util.*;

public class Remove_Iterator {
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

        Remove_SpecifiedNumber_In_ArrayList(list);

        list.forEach(System.out::println);
    }

    /**
     * 删除集合中小于10的元素
     * @param list
     */
    public static void Remove_SpecifiedNumber_In_ArrayList(List<Integer> list) {
        Iterator<Integer> iterator = list.iterator();
//        当通过迭代器删除集合中的元素之后，集合变小，因此应该在初始时就统计集合的大小
        int len = list.size();
        for (int i = 0; i < len; ++i) {
            Integer temp = iterator.next();
            if (temp < 10) {
                iterator.remove();
            }
        }
    }
}
