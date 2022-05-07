/*
 * @lc app=leetcode.cn id=32 lang=java
 *
 * [32] 最长有效括号
 */

// @lc code=start
class Solution {
    public int longestValidParentheses(String s) {
        char[] ca = s.toCharArray();
        int len = ca.length, max = 0;
        int[] dp = new int[len];
        for (int i = 1; i < len; ++i) {
            if (ca[i] == ')') {
                if (ca[i - 1] == '(') {
                    dp[i] = i > 2 ? dp[i - 2] + 2 : 2; // ()()
                } else if (dp[i - 1] > 0) {
                    if (i - dp[i - 1] - 1 >= 0 && ca[i - dp[i - 1] - 1] == '(') {
                        dp[i] = dp[i - 1] + 2; // (())
                        if (i - dp[i - 1] - 2 > 0) {
                            dp[i] += dp[i - dp[i - 1] - 2]; // ()(())
                        }
                    }
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
// @lc code=end

