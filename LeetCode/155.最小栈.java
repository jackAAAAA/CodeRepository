import java.util.Deque;

/*
 * @lc app=leetcode.cn id=155 lang=java
 *
 * [155] 最小栈
 */

// @lc code=start
class MinStack {

    class MyNode {
        int val, min;
        MyNode next;
        MyNode(int val, int min) {
            this.val = val;
            this.min = min;
            this.next = null;
        }
        MyNode(int val, int min, MyNode next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }

    public MinStack() {}

    MyNode head;

    public void push(int x) {
        if (head == null) {
            head = new MyNode(x, x);
        } else {
            head = new MyNode(x, Math.min(x, head.min), head);
        }
    }

    public void pop() {
        head = head.next;
    }

    public int top() {
        return head.val;
    }

    public int getMin() {
        return head.min;
    }
}
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
// @lc code=end

