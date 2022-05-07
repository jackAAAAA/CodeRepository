package Stack;

class MinStack {

    class MyNode {
        private int val, min;
        private MyNode next;
        public MyNode() {}; // 用这种方式构建的MyNode只是声明有这些成员变量，并且这些成员变量没赋值，不过有系统的默认值，成员变量为Null Pointer，即不能被引用
        public MyNode(int val, int min) {
            this.val = val;
            this.min = min;
            this.next = null;
        }
        public MyNode(int val, int min, MyNode next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }

    // 这样默认node为null
    private MyNode node;

    /** initialize your data structure here. */
    public MinStack() {
//        这种方法为正解，可以给node赋值为null，或者不赋值而由系统在node声明时给出默认值
        node = null;
//        采用这种方法，会有一个默认的 0 | 0 | null 节点，带来不想要的麻烦
//         node = new MyNode();
    }

    public void push(int val) {
        if (node == null) {
            node = new MyNode(val, val);
        } else {
            node = new MyNode(val, Math.min(val, node.min), node);
        }
//        node = new MyNode(val, Math.min(val, node.min), node);
    }

    public void pop() {
        node = node.next;
    }

    public int top() {
        return node.val;
    }

    public int getMin() {
        return node.min;
    }

    public static void main(String[] args) {
        MinStack obj = new MinStack();
        obj.push(3);
        int de_min = obj.getMin();
        int de_val = obj.top();
        System.out.println(de_min + " " + de_val);
    }

}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
