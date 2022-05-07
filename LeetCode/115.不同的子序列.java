/*
 * @lc app=leetcode.cn id=115 lang=java
 *
 * [115] 不同的子序列
 */

// @lc code=start
class Solution {
    public int numDistinct(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int sl = cs.length, tl = ct.length;
        int[] dp = new int[tl + 1];
        dp[0] = 1;
        for (int i = 1; i <= sl; ++i) {
            for (int j = tl; j > 0; --j) {
                if (cs[i - 1] == ct[j - 1]) {
                    dp[j] += dp[j - 1];
                }
                if (i == sl) {
                    break;
                }
            }
        }
        return dp[tl];
    }
}
// @lc code=end

