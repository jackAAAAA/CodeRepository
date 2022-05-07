/*
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildBinaryTree(preorder, inorder, Integer.MAX_VALUE);
    }
    private int pre = 0, in = 0;
    private TreeNode buildBinaryTree(int[] preorder, int[] inorder, int stop) {
        if (pre == preorder.length) {
            return null;
        }
        if (inorder[in] == stop) {
            ++in;
            return null;
        }
        TreeNode root = new TreeNode(preorder[pre++]);
        root.left = buildBinaryTree(preorder, inorder, root.val);
        root.right = buildBinaryTree(preorder, inorder, stop);
        return root;
    }
}
// @lc code=end

