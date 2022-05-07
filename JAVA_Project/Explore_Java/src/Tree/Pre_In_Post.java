package Tree;

import java.util.ArrayList;
import java.util.List;

public class Pre_In_Post {

    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.val = 1;
        TreeNode left = new TreeNode();
        left.val = 2;
        TreeNode right = new TreeNode();
        right.val = 3;
        root.left = left;
        root.right = right;
        Pre_In_Post pre_in_post = new Pre_In_Post();
        pre_in_post.threeOrders(root);
    }

    public void threeOrders (TreeNode root) {
        List<List<Integer>> temp = new ArrayList<>();
        temp.add(new ArrayList<>(preOrder(root, new ArrayList<>())));
        temp.add(new ArrayList<>(inOrder(root, new ArrayList<>())));
        temp.add(new ArrayList<>(postOrder(root, new ArrayList<>())));
        int len = temp.get(0).size();
//        int[][] res = new int[3][len];
        for(int i = 0; i < 3; ++i) {
            for (int j = 0; j < len; ++j) {
                System.out.println(temp.get(i).get(j));
            }
        }
//        return res;
    }
    private List<Integer> preOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return res;
        }
        res.add(root.val);
        preOrder(root.left, res);
        preOrder(root.right, res);
        return res;
    }
    private List<Integer> inOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return res;
        }
        inOrder(root.left, res);
        res.add(root.val);
        inOrder(root.right, res);
        return res;
    }
    private List<Integer> postOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return res;
        }
        postOrder(root.left, res);
        postOrder(root.right, res);
        res.add(root.val);
        return res;
    }

    private static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }
}
