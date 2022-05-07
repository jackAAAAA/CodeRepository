package LinkedList;

import static java.lang.Integer.parseInt;

public class Cycle {

    private static ListNode node;
    public static void main (String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        ListNode newNode = reverseList(Cycle.createList(node, arr));
        printList(newNode);
//        System.out.println(parseInt("1111", 16));
    }

    public static ListNode reverseList(ListNode head) {
        ListNode newNode = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newNode;
            newNode = head;
            head = next;
        }
        return newNode;
    }

    private static ListNode createList (ListNode node, int[] arr) {
        for (int i = arr.length - 1; i >= 0; --i) {
//            if (node == null) {
//                node = new ListNode(arr[i]);
//            } else {
//                node = new ListNode(arr[i], node);
//            }
            node = new ListNode(arr[i], node);
        }
        return node;
    }

    private static void printList (ListNode node) {
        while (node != null) {
            if (node.next != null) {
                System.out.print(node.val + "→");
            } else {
                System.out.println(node.val);
            }
            node = node.next;
        }
    }

    private static class ListNode {
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
    }

}
