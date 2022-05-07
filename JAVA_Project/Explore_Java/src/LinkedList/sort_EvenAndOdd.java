package LinkedList;

public class sort_EvenAndOdd {
    public static void main(String[] args) {
//        数据初始化
//        1. 偶数位递增，奇数位递减
//        ListNode l1 = new ListNode(3);
//        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(1);
//        ListNode l4 = new ListNode(4);
//        l1.next = l2; l2.next = l3; l3.next = l4;
////        打印初始的结果
//        print(l1);

//        2. 偶数位递减，奇数位递增
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(2);
        l1.next = l2; l2.next = l3; l3.next = l4;
//        打印初始的结果
        print(l1);
//        数据处理
        sort_EvenAndOdd base = new sort_EvenAndOdd();
        ListNode[] list = base.evenOddList(l1);

//        1.
//        让奇数位元素递减被正序
//        ListNode reverseList = base.reverse(list[0]);
//        最终的结果保存在res中
//        ListNode res = base.mergeTwoLists(list[1], reverseList);

//        2.
//        让偶数位元素递减被正序
        ListNode reverseList = base.reverse(list[1]);
        ListNode res = base.mergeTwoLists(list[0], reverseList);

//        打印最终的结果
        print(res);
    }

    public static void print(ListNode head) {
        ListNode temp = head;
        while (temp.next != null) {
            System.out.print(temp.val + " -> ");
            temp = temp.next;
        }
        System.out.println(temp.val);
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode dummy = new ListNode(-1);
        ListNode prev = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        if (l1 != null) {
            prev.next = l1;
        } else {
            prev.next = l2;
        }
        return dummy.next;
    }

    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = next;
            next = head;
            head = temp;
        }
        return next;
    }

    private ListNode[] evenOddList(ListNode head) {
        if (head == null || head.next == null) {
            return new ListNode[2];
        }
        ListNode l1 = head;
        ListNode head2 = head.next;
        ListNode l2 = head2;
        while (l2 != null && l2.next != null) {
            l1.next = l2.next;
            l1 = l2.next;
            l2.next = l1.next;
            l2 = l1.next;
        }
        l1.next = null;
        return new ListNode[] {head, head2};
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {

        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
