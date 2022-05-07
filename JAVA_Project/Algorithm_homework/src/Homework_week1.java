import java.util.*;

public class Homework_week1 {

    public static void main(String[] args) {

        int[] a = {2, 4, 6, 10};

        //查找并输出数组序号
        String s1 = Arrays.toString(Sum_of_two_number(a, 8));
        System.out.println(s1);

        //合并有序链表并输出
        ListNode l1 = list(5, 1);
        ListNode l2 = list(10, 5);
        ListNode s2 = mergeTwoLists(l1, l2);

        while (s2 != null) {
            System.out.print(s2.val);
            s2 = s2.next;
        }

    }

    public static ListNode list(int n, int step) {
        ListNode nodeSta = new ListNode(0);          //创建首节点
        ListNode nextNode;                           //声明一个变量用来在移动过程中指向当前节点
        nextNode = nodeSta;                            //指向首节点

        //创建链表
        for (int i = 1; i < n; i += step) {
            ListNode node = new ListNode(i);         //生成新的节点
            nextNode.next = node;                      //把新节点连起来
            nextNode = nextNode.next;                  //当前节点往后移动
        } //当for循环完成之后 nextNode指向最后一个节点，

        nextNode = nodeSta;                            //重新赋值让它指向首节点

        return nextNode;
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        //当有一个链表没有合并完毕时，直接将该链表剩余元素链接到已合并的链表末尾
        prev.next = l1 == null ? l2 : l1;

        return prehead.next;

    }


    public static int[] Sum_of_two_number(int[] nums, int target) {
        //数组输入异常的处理
        if (nums.length < 2) {
            return new int[2];
        }
        int len = nums.length;
        //哈希表，极限情况是——遍历到数组的最后一个元素，不管最后结果如何都无需将最后一个元素放入哈希表
        Map<Integer, Integer> hashMap = new HashMap<>(len - 1);
        hashMap.put(nums[0], 0);//第一个元素前没有元素，故直接放入哈希表
        for (int i = 1; i < len; i++) {
            int another = target - nums[i];
            if (hashMap.containsKey(another)) {
                return new int[]{hashMap.get(another), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[2];
    }
}

//        while (l1 != null) {
//            System.out.print(l1.val);
//            l1 = l1.next;
//        }
//
//        System.out.println();
//
//        while (l2 != null) {
//            System.out.print(l2.val);
//            l2 = l2.next;
//        }
//
//        System.out.println();

//        l1 = list(5, 1);
//        l2 = list(10, 2);