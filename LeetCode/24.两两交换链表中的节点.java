    /*
 * @lc app=leetcode.cn id=24 lang=java
 *
 * [24] 两两交换链表中的节点
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummyhead = new ListNode(-1);
        dummyhead.next = head;
        ListNode node = dummyhead;
        while (node.next != null && node.next.next != null) {
            ListNode node1 = node.next;
            ListNode node2 = node.next.next;
            node.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            node = node1;
        }
        return dummyhead.next;
    }
}
// @lc code=end

