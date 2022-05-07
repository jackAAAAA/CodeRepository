package LinkedList;

public class LeetCode_25 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        static ListNode createList(ListNode node, int[] arr) {
            for (int i = arr.length - 1; i >= 0; --i) {
                if (node == null) {
                    node = new ListNode(arr[i]);
                } else {
                    node = new ListNode(arr[i], node);
                }
            }
            return node;
        }

        static void printList(ListNode node) {
            while (node != null) {
                if (node.next != null) {
                    System.out.print(node.val + "→");
                } else {
                    System.out.println(node.val);
                }
                node = node.next;
            }
        }

    }

    public static void main(String[] args) {
        int k = 2;
//        构建链表
        ListNode head = null;
        int[] arr = {1, 2, 3, 4, 5, 6};
        ListNode newNode = ListNode.createList(head, arr);
        newNode = reverseKGroup(newNode, k);
//        输出链表
        ListNode.printList(newNode);
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        ListNode dummyhead = new ListNode(-1);
        dummyhead.next = head;
        ListNode begin = dummyhead;
        int i = 0;
        while (head != null) {
            ++i;
            if (i % k == 0) {
                begin = reverse(begin, head.next);
                head = begin.next;
            } else {
                head = head.next;
            }
        }
        return dummyhead.next;
    }

    private static ListNode reverse(ListNode begin, ListNode end) {
        ListNode prev = begin;
        ListNode cur = begin.next;
        ListNode first = cur;
        while (cur != end) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        begin.next = prev;
        first.next = cur;
        return first;
    }
}