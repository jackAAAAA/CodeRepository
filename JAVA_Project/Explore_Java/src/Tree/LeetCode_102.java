package Tree;

import java.util.*;

public class LeetCode_102 {
    public static void main(String[] args) {
//        root = [3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        TreeNode root_right = root.right;
        root_right.left = new TreeNode(15);
        root_right.right = new TreeNode(7);
        List<List<Integer>> result = levelOrder(root);
        int row = result.size();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                System.out.print(result.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    private static List<List<Integer>> levelOrder (TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null) {
            return res;
        }
//        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<Integer>();
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(level);
        }
        return res;
    }

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode (int val) {
            this.val = val;
        }
    }

}
