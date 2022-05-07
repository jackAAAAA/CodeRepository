package Tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// LeetCode_105 & 297
public class BinaryTree {

    private static String NN = "X";
    private static String spliter = ",";

    public static void main(String[] args) {
//        LeetCodd_105: 前序+后序构造二叉树并按照先序遍历打印出对应的二叉树
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
//        TreeNode node = bt.buildTree(preorder, inorder);
//        node.print();

//        LeetCodd_297: 序列化+反序列化二叉树并按照先序遍历打印出对应的二叉树
        String Input = "1,2,X,X,3,4,X,X,5,X,X,";
        String Input1 = "1,2,3,X,X,4,5";
//        BinaryTree ser = new BinaryTree();
//        BinaryTree deser = new BinaryTree();
//        TreeNode ans = deser.deserialize(Input);
//        ans.print();
//        String str = ser.serialize(ans);
//        System.out.println(str);
//        TreeNode ans1 = deser.deserialize(str);
        TreeNode bt = new TreeNode();
        Deque<String> nodes = new ArrayDeque<String>();
        nodes.addAll(Arrays.asList(Input.split(spliter)));
        TreeNode res = bt.buildTree(nodes);
        res.print();
    }

    public static class TreeNode {
        private int val;
        private TreeNode left, right;
        public int test;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        //       按照先序遍历来构建二叉树
//      为啥要用双端队列来构造二叉树？因为双端队列方法数多
        public TreeNode buildTree(Deque<String> nodes) {
            if (nodes.isEmpty()) {
                return null;
            }
            String value = nodes.remove();
            if (value.equals(NN)) {
                return null;
            } else {
                TreeNode node = new TreeNode(Integer.parseInt(value));
                node.left = buildTree(nodes);
                node.right = buildTree(nodes);
                return node;
            }
        }

        //      按照先序遍历的顺序打印出二叉树
        public void print() {
            print("", this, true);
        }

        public void print(String prefix, TreeNode n, boolean isLeft) {
            if (n != null) {
//              根节点与左节点用|--指示；右节点用\--指示。
                System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + n.val);
                print(prefix + "    ", n.left, true);
                print(prefix + "    ", n.right, false);
            }
        }

    }

//    2021.07.05
//    static class TreeNode {
//        int val;
//        TreeNode left, right;
//
//        TreeNode() {
//        }
//
//        TreeNode(int value) {
//            this.val = value;
//        }
//
//        TreeNode buildTree(Deque<String> Input) {
//            if (Input == null || Input.isEmpty()) {
//                return null;
//            }
//            String value = Input.removeFirst();
//            if (value.equals("X")) {
//                return null;
//            } else {
//                TreeNode root = new TreeNode(Integer.parseInt(value));
//                root.left = buildTree(Input);
//                root.right = buildTree(Input);
//                return root;
//            }
//        }
//
//        void print() {
//            this.print("", this, true);
//        }
//
//        void print(String prefix, TreeNode root, boolean isLeft) {
//            if (root == null) {
//                return;
//            }
//            System.out.println(prefix + (isLeft ? "|--" : "\\--") + root.val);
//            print(prefix + "    ", root.left, true);
//            print(prefix + "    ", root.right, false);
//        }
//    }

//    2021.06.06
//    class TreeNode {
//        private int value;
//        private TreeNode left, right;
//        public TreeNode() {}
//        public TreeNode(int value) {
//            this.value = value;
//        }
//        public TreeNode buildTree(Deque<String> nodes) {
//            if (nodes.isEmpty()) {
//                return null;
//            }
//            String value = nodes.remove();
//            if (value.equals("X")) {
//                return null;
//            } else {
//                TreeNode node = new TreeNode(Integer.parseInt(value));
//                node.left = buildTree(nodes);
//                node.right = buildTree(nodes);
//                return node;
//            }
//        }
//        public void print() {
//            print("", this, true);
//        }
//
//        public void print(String prefix, TreeNode node, boolean isLeft) {
//            if (node != null) {
//                System.out.println(prefix + (isLeft ? "|--" : "\\--") + node.value);
//                print(prefix + "    ", node.left, true);
//                print(prefix + "    ", node.right, false);
//            }
//        }
//    }

    //  LeetCode_297
    /*
     * 经过仔细比对：发现LeetCode_297的解法所构造出的二叉树与题目要求的不一致，但仍然AC
     * 原因在于OJ只检测了输入和输出的树形所构成的字符串是否一致？一致则通过
     * 给定一个字符串后，把它看成一个先序序列还是一个中、后、层序序列，这是由程序员说了算的
     * 今天下午之所以会耗费如此多的时间：在于我的pint()函数是按照先序序列的方式来打印二叉树的，而我输入字符串的时候——
     * ——并没有将它看成先序遍历的结果，于是便导致实际打印输出的先序序列图形和自己的所想的不一致，而耗费了不少时间
     */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    public void buildString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NN).append(spliter);
        } else {
            sb.append(node.val).append(spliter);
            buildString(node.left, sb);
            buildString(node.right, sb);
        }
    }

    public TreeNode deserialize(String data) {
        Deque<String> nodes = new ArrayDeque<String>();
        nodes.addAll(Arrays.asList(data.split(spliter)));
        return buildTree(nodes);
    }

    public TreeNode buildTree(Deque<String> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        String value = nodes.remove();
        if (value.equals(NN)) {
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.parseInt(value));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }

    //  LeetCode_105
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeHelper(preorder, inorder, Integer.MAX_VALUE);
    }

    static int pre = 0;
    static int in = 0;

    private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int stop) {
        //递归终止条件
        if (pre == preorder.length) return null;
        if (inorder[in] == stop) {
            ++in;
            return null;
        }
        int root_val = preorder[pre++];
        TreeNode root = new TreeNode(root_val);
        root.left = buildTreeHelper(preorder, inorder, root_val);
        root.right = buildTreeHelper(preorder, inorder, stop);
        return root;
    }

}

//    对private修饰符的探索
//    class tree extends BinaryTree.TreeNode {
//        private int t1 = super.test;
////        private int v1 = super.val; //tree为TreeNode的子类且tree与TreeNode在同一个文件中并列存在；val被private修饰，故不可访问
//        public static void main(String[] args) {
////             TO DO
////            虽然可以运行，但不允许这样做
//        }
//
//    }
