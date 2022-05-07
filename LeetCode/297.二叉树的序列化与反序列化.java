/*
 * @lc app=leetcode.cn id=297 lang=java
 *
 * [297] 二叉树的序列化与反序列化
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Codec {
    
    private String NN = "X";
    private String spliter = ",";

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    private void buildString(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NN).append(spliter);
            return;
        }
        sb.append(root.val).append(spliter);
        buildString(root.left, sb);
        buildString(root.right, sb);
    }

    public TreeNode deserialize(String data) {
        Queue<String> root = new LinkedList<>();
        root.addAll(Arrays.asList(data.split(spliter)));
        return buildTree(root);
    }

    private TreeNode buildTree(Queue<String> root) {
        String value = root.remove();
        if (value.equals(NN)) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(value));
        node.left = buildTree(root);
        node.right = buildTree(root);
        return node;
    }
    
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
// @lc code=end

