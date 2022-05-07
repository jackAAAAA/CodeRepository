/*
 * @lc app=leetcode.cn id=10 lang=java
 *
 * [10] 正则表达式匹配
 */

// @lc code=start
class Solution {
    public boolean isMatch(String s, String t) {
        char[] ca_s = s.toCharArray(), ca_t = t.toCharArray();
        int sl = ca_s.length, tl = ca_t.length;
        boolean[][] dp = new boolean[sl + 1][tl + 1];
        dp[0][0] = true;
        for (int j = 1; j < tl; j += 2) {
            if (ca_t[j] == '*') {
                dp[0][j + 1] = dp[0][j - 1];
            }
        }
        for (int i = 1; i <= sl; ++i) {
            for (int j = 1; j <= tl; ++j) {
                if (ca_t[j - 1] == '*') {
                    dp[i][j] = dp[i][j - 2] || (ismatch(ca_s[i - 1], ca_t[j - 2]) && dp[i - 1][j]);
                } else {
                    dp[i][j] = ismatch(ca_s[i - 1], ca_t[j - 1]) && dp[i - 1][j - 1];
                }
            }
        }
        return dp[sl][tl];
    }
    public boolean ismatch(char s, char t) {
        return t == '.' || s == t;
    } 
}
// @lc code=end

