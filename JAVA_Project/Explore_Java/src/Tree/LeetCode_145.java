package Tree;

import java.util.*;


public class LeetCode_145 {

    //    Original Template
//    Recurse
    public static List<Integer> postorderTraversal_1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    public static void helper(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        helper(root.left, res);
        helper(root.right, res);
        res.add(root.value);
    }

//    2021.07.04
//    public static List<Integer> postorderTraversal_3(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        helper(res, root);
//        return res;
//    }
//
//    private static void helper(List<Integer> res, TreeNode root) {
//        if (root == null) {
//            return;
//        }
//        helper(res, root.left);
//        helper(res, root.right);
//        res.add(root.value);
//    }

//    2021.06.21

//    public static List<Integer> postorderTraversal_3(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        helper3(root, res);
//        return res;
//    }
//
//    public static void helper3(TreeNode root, List<Integer> res) {
//        if (root == null) {
//            return;
//        }
//        helper(root.left, res);
//        helper(root.right, res);
//        res.add(root.value);
//    }

//    2021.06.11
//    public static List<Integer> postorderTraversal_3(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        helper(root, res);
//        return res;
//    }
//
//    public static void helper3(TreeNode root, List<Integer> res) {
//        if (root == null) {
//            return;
//        }
//        helper3(root.left, res);
//        helper3(root.right, res);
//        res.add(root.val);
//    }

//    2021.06.08
//    public static List<Integer> postorderTraversal_3(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        helper1(root, res);
//        return res;
//    }

//    public static void helper1(TreeNode root, List<Integer> res) {
//        if (root == null) {
//            return;
//        }
//        helper1(root.left, res);
//        helper1(root.right, res);
//        res.add(root.val);
//    }

// 2021.06.07
//    public List<Integer> postorderTraversal_4(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        helper1(root, res);
//        return res;
//    }
//
//    public void helper1(TreeNode root, List<Integer> res) {
//        if (root == null) {
//            return;
//        }
//        helper1(root.left, res);
//        helper1(root.right, res);
//        res.add(root.val);
//    }

    //    Original Template
//    Iteration
    public static List<Integer> postorderTraversal_2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offerLast(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pollLast();
            res.add(0, node.value);
            if (node.left != null) {
                stack.offerLast(node.left);
            }
            if (node.right != null) {
                stack.offerLast(node.right);
            }
        }
        return res;
    }

//    2021.07.04
//    public static List<Integer> postorderTraversal_4(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        if (root == null) {
//            return res;
//        }
//        Deque<TreeNode> stack = new ArrayDeque<>();
//        stack.offerLast(root);
//        while (!stack.isEmpty()) {
//            root = stack.pollLast();
//            res.add(root.value);
//            if (root.left != null) {
//                stack.offerLast(root.left);
//            }
//            if (root.right != null) {
//                stack.offerLast(root.right);
//            }
//        }
//        Collections.reverse(res);
//        return res;
//    }

//    2021.06.21
//    public static List<Integer> postorderTraversal_4(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        if (root == null) {
//            return res;
//        }
//        Deque<TreeNode> stack = new ArrayDeque<>();
//        stack.offerFirst(root);
//        while (!stack.isEmpty()) {
//            root = stack.pollFirst();
//            res.add(0, root.value);
//            if (root.left != null) {
//                stack.offerFirst(root.left);
//            }
//            if (root.right != null) {
//                stack.offerFirst(root.right);
//            }
//        }
//        return res;
//    }

//    2021.06.11
//    public static List<Integer> postorderTraversal_4(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        if (root == null) {
//            return res;
//        }
//        Deque<TreeNode> stack = new ArrayDeque<>();
//        stack.offerFirst(root);
//        while (!stack.isEmpty()) {
//            root = stack.pollFirst();
//            res.add(0, root.val);
//            if (root.left != null) {
//                stack.offerFirst(root.left);
//            }
//            if (root.right != null) {
//                stack.offerFirst(root.right);
//            }
//        }
//        return res;
//    }

//    2021.06.08
//    public static List<Integer> postorderTraversal_4(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        if (root == null) {
//            return res;
//        }
//        Deque<TreeNode> stack = new ArrayDeque<>();
//        stack.offerLast(root);
//        while (!stack.isEmpty()) {
//            root = stack.pollLast();
//            res.add(0, root.val);
//            if (root.left != null) {
//                stack.offerLast(root.left);
//            }
//            if (root.right != null) {
//                stack.offerLast(root.right);
//            }
//        }
//        return res;
//    }

//    2021.06.07
//    public static List<Integer> postorderTraversal_3(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        if (root == null) {
//            return res;
//        }
//        Deque<TreeNode> stack = new ArrayDeque<>();
//        stack.offerFirst(root);
//        while (!stack.isEmpty()) {
//            root = stack.pollFirst();
//            res.add(0, root.val);
//            if (root.left != null) {
//                stack.offerFirst(root.left);
//            }
//            if (root.right != null) {
//                stack.offerFirst(root.right);
//            }
//        }
//        return res;
//    }


    public static void main(String[] args) {
//        Initial data
        String Input = "1,2,X,X,3,4,X,X,5,X,X,";
        Deque<String> nodes = new ArrayDeque<>();
        nodes.addAll(Arrays.asList(Input.split(",")));
        TreeNode node = new TreeNode();
        TreeNode root = node.buildTree(nodes);
        root.print();

//        2021.06.21
//        String Input = "1,2,X,X,3,4,X,X,5,X,X";
//        Deque<String> nodes = new ArrayDeque<>();
//        nodes.addAll(Arrays.asList(Input.split(",")));
//        TreeNode node = new TreeNode();
//        TreeNode root = node.buildTree(nodes);
//        root.print();

//  2021.06.12
//        String Input = "1,2,X,X,3,4,X,X,5,X,X";
//        Deque<String> nodes = new ArrayDeque<>();
//        nodes.addAll(Arrays.asList(Input.split(",")));
//        TreeNode node = new TreeNode();
//        TreeNode root = node.buildTree(nodes);
//        root.print();

//        2021.06.09
//        String Input = "1,2,X,X,3,4,X,X,5,X,X";
//        Deque<String> nodes = new ArrayDeque<>();
//        nodes.addAll(Arrays.asList(Input.split(",")));
//        TreeNode node = new TreeNode();
//        TreeNode root = node.buildTree(nodes);
//        root.print();

//        PostOrderTraversal
//        List<Integer> res = postorderTraversal_1(root);
        List<Integer> res = postorderTraversal_2(root);
//        List<Integer> res = postorderTraversal_3(root);
//        List<Integer> res = postorderTraversal_4(root);

//        Output

//        2021.06.21
//        res.forEach(Integer -> System.out.print(Integer + " "));
//        System.out.println();

//        Lambda expression
        res.forEach(Integer -> System.out.print(Integer + " "));
//        Normal expression
//        for (int num : res) {
//            System.out.print(num + " ");
//        }
//        System.out.println();

    }

}

//    original Template
class TreeNode {
    int value;
    TreeNode left, right;

    public TreeNode() {
    }

    public TreeNode(int value) {
        this.value = value;
    }

    public TreeNode buildTree(Deque<String> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        String value = nodes.remove();
        if (value.equals("X")) {
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.parseInt(value));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }

    public void print() {
        print("", this, true);
    }

    public void print(String prefix, TreeNode node, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + node.value);
            print(prefix + "    ", node.left, true);
            print(prefix + "    ", node.right, false);
        }
    }
}

//class TreeNode {
//    int value;
//    TreeNode left, right;
//    public TreeNode() {}
//
//    public TreeNode(int value) {
//        this.value = value;
//    }
//
//    TreeNode buildTree(Deque<String> nodes) {
//        if (nodes.isEmpty()) {
//            return null;
//        }
//        String value = nodes.remove();
//        if (value.equals("X")) {
//            return null;
//        } else {
//            TreeNode root = new TreeNode(Integer.parseInt(value));
//            root.left = buildTree(nodes);
//            root.right = buildTree(nodes);
//            return root;
//        }
//    }
//
//    void print() {
//        print("", this, true);
//    }
//
//    void print(String prefix, TreeNode root, boolean isLeft) {
//        if (root != null) {
//            System.out.println(prefix + (isLeft ? "|--" : "\\--") + root.value);
//            print(prefix + "    ", root.left, true);
//            print(prefix + "    ", root.right, false);
//        }
//    }
//
//}

//  2021.06.12
//    class TreeNode {
//        int value;
//        TreeNode left, right;
//        TreeNode() {}
//        TreeNode(int value) {
//            this.value = value;
//        }
//        public TreeNode buildTree(Deque<String> nodes) {
//            if (nodes.isEmpty()) {
//                return null;
//            }
//            String val = nodes.remove();
//            if (val.equals("X")) {
//                return null;
//            } else {
//                TreeNode node = new TreeNode(Integer.parseInt(val));
//                node.left = buildTree(nodes);
//                node.right = buildTree(nodes);
//                return node;
//            }
//        }
//
//        public void print() {
//            print("", this, true);
//        }
//
//        public void print(String prefix, TreeNode node, boolean isLeft) {
//            if (node != null) {
//                System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + node.value);
//                print(prefix + "    ", node.left, true);
//                print(prefix + "    ", node.right, false);
//            }
//        }
//    }

//    2021.06.09
//class TreeNode {
//    int val;
//    TreeNode left, right;
//    public TreeNode() {}
//
//    public TreeNode(int val) {
//        this.val = val;
//    }
//
//    public TreeNode buildTree(Deque<String> nodes) {
//        if (nodes.isEmpty()) {
//            return null;
//        }
//        String value = nodes.remove();
//        if (value.equals("X")) {
//            return null;
//        } else {
//            TreeNode node = new TreeNode(Integer.parseInt(value));
//            node.left = buildTree(nodes);
//            node.right = buildTree(nodes);
//            return node;
//        }
//    }
//
//    public void print() {
//        print("", this, true);
//    }
//
//    public void print(String prefix, TreeNode node, boolean isLeft) {
//        if (node != null) {
//            System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + node.val);
//            print(prefix + "    ", node.left, true);
//            print(prefix + "    ", node.right, false);
//        }
//    }
//}


//    2021.06.07
//    static class TreeNode {
//        private int val;
//        private TreeNode left, right;
//
//        public TreeNode() {
//
//        }
//
//        public TreeNode(int val) {
//            this.val = val;
//        }
//
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
//
//        public void print() {
//            print("", this, true);
//        }
//
//        public void print(String prefix, TreeNode node, boolean isLeft) {
//            if (node != null) {
//                System.out.println(prefix + (isLeft ? "|-- " : "\\-- ") + node.val);
//                print(prefix + "    ", node.left, true);
//                print(prefix + "    ", node.right, false);
//            }
//        }
//    }

