package Queue;

import Queue.MemorialWriting.AQ_0901;
import Queue.MemorialWriting.AQ_1001_2;
import Queue.MemorialWriting.AQ_1001_2;

public class Test {
    /**
     * 1.将元素入队；
     * 2.从队列中的删除元素；
     * 3.获取此时队列中元素的个数；
     * 4.head指向队首元素，tail指向队尾元素的下一个元素
     * 5.null无法强制转型成int
     * @param args
     */

    /**
     * 9
     * 11
     * 7
     * Head: 1
     * Tail: 8
     * @param args
     */

    public static void main(String[] args) {
        AQ_1001_2 aq_1001_2 = new AQ_1001_2(2);
        aq_1001_2.deque();
        aq_1001_2.enqueue(9);
        aq_1001_2.deque();
        aq_1001_2.deque();
        aq_1001_2.deque();
        aq_1001_2.enqueue(9);
        aq_1001_2.enqueue(9);
        aq_1001_2.enqueue(9);
        aq_1001_2.enqueue(9);
        aq_1001_2.enqueue(9);
        aq_1001_2.enqueue(9);
        aq_1001_2.enqueue(6);
        aq_1001_2.enqueue(3);
        int deNum = (int) aq_1001_2.deque();
        System.out.println(deNum + "\n" + aq_1001_2.getCapacity() + "\n"
                + aq_1001_2.getSize());
        aq_1001_2.printHead_Tail();
    }
}
