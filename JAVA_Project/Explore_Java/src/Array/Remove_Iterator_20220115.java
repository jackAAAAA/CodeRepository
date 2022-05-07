package Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Remove_Iterator_20220115 {
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
//            if (list.get(i) < 10) {
//                不能用remove(i)来遍历删除元素的原因：
//                1.删除前面的元素之后，list的长度就发生了变化；
//                2.因此当再次删除元素之时，原来在初始list中的元素序号就发生了变化，不再是初始的序号
//                list.remove(i);
//            }
            int temp = it.next();
            if (temp < 1000) {
                it.remove();
            }
//            System.out.println(list.get(i));
        }
        list.forEach(System.out::println);
    }
}
