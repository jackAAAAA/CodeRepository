package LinkedList;

/**
 * 腾讯一面试题
 */

public class Test {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        l1.next.next.next = new ListNode(4);
        ListNode l2 = new Test().reverse(l1);
//        while (l1 != null) {
//            System.out.println(l1.val);
//            l1 = l1.next;
//        }
        while (l2 != null) {
            System.out.println(l2.val);
            l2 = l2.next;
        }
    }

    private ListNode reverse(ListNode head) {
        ListNode dummyhead = new ListNode(-1);
        dummyhead.next = head;
        ListNode temp = dummyhead;
        while (temp.next != null && temp.next.next != null) {
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            temp.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            temp = node1;
        }
        return dummyhead.next;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {

        }

        ListNode(int val) {
            this.val = val;
        }
    }
}
