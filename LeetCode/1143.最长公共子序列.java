/*
 * @lc app=leetcode.cn id=1143 lang=java
 *
 * [1143] 最长公共子序列
 */

// @lc code=start
class Solution {
    public int longestCommonSubsequence(String s, String t) {
        char[] ca1 = s.toCharArray(), ca2 = t.toCharArray();
        int sl = ca1.length, tl = ca2.length;
        int[][] dp = new int[sl + 1][tl + 1];
        for (int i = 1; i <= sl; ++i) {
            for (int j = 1; j <= tl; ++j) {
                if (ca1[i - 1] == ca2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[sl][tl];
    }
}
// @lc code=end

