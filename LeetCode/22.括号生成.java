/*
 * @lc app=leetcode.cn id=22 lang=java
 *
 * [22] 括号生成
 */

// @lc code=start
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        backtrack(list, new StringBuilder(), n, 0, 0);
        return list;
    }
    private void backtrack(List<String> list, StringBuilder sb, int n, int left, int right) {
        if (left == n && right == n) {
            list.add(sb.toString());
            return;
        }
        if (left < n) {
            sb.append("(");
            backtrack(list, sb, n, left + 1, right);
            sb.setLength(sb.length() - 1);
        }
        if (right < left) {
            sb.append(")");
            backtrack(list, sb, n, left, right + 1);
            sb.setLength(sb.length() - 1);
        }
    }
}
// @lc code=end

