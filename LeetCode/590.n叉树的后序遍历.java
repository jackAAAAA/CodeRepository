/*
 * @lc app=leetcode.cn id=590 lang=java
 *
 * [590] N叉树的后序遍历
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
    public List<Integer> postorder(Node root) {
        //Terminator
        if (root == null) return res;
        //Current Logic
        for (Node node : root.children) {
            //Drill Down
            postorder(node);
        }
        //Current Logic
        res.add(root.val);
        return res;
        //Restore Current Data
    }
    private List<Integer> res = new ArrayList<Integer>();
}
// @lc code=end

