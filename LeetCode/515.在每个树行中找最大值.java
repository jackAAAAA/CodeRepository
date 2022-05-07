/*
 * @lc app=leetcode.cn id=515 lang=java
 *
 * [515] 在每个树行中找最大值
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
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        helper(list, root, 0);
        return list;
    }
    private void helper(List<Integer> list, TreeNode root, int depth) {
        //Terminator
        if (root == null) {
            return;
        }
        //Current Logic
        if (depth == list.size()) {
            list.add(root.val);
        } else {
            list.set(depth, Math.max(list.get(depth), root.val));
        }
        //Drill Down
        helper(list, root.left, depth + 1);
        helper(list, root.right, depth + 1);
        //Restore Current Data
    }
}
// @lc code=end

