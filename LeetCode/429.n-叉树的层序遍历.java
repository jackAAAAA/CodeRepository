/*
 * @lc app=leetcode.cn id=429 lang=java
 *
 * [429] N 叉树的层序遍历
 */

// @lc code=start
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    
    private List<List<Integer>> list = new ArrayList<>();
    public List<List<Integer>> levelOrder(Node root) {
        if (root != null) dfs(root, 0);
        return list;
    }
    private void dfs(Node root, int level) {
        if (list.size() <= level) {
            list.add(new ArrayList<>());
        }
        list.get(level).add(root.val);
        for (Node child : root.children) {
            dfs(child, level + 1);
        }
    }

}
// @lc code=end

