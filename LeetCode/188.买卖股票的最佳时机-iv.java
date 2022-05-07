/*
 * @lc app=leetcode.cn id=188 lang=java
 *
 * [188] 买卖股票的最佳时机 IV
 */

// @lc code=start
class Solution {
    public int maxProfit(int K, int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }
        int trade_num = Math.min(K, len >> 1);
        int[][][] dp = new int[len][2][trade_num + 1];
        for (int k = 1; k <= trade_num; ++k) {
            dp[0][1][k] = -prices[0];
        }
        for (int i = 1; i < len; ++i) {
            for (int k = 1; k <= trade_num; ++k) {
                dp[i][0][k] = Math.max(dp[i - 1][0][k], dp[i - 1][1][k] + prices[i]);
                dp[i][1][k] = Math.max(dp[i - 1][1][k], dp[i - 1][0][k - 1] - prices[i]);
            }
        }
        return dp[len - 1][0][trade_num];
    }
}
// @lc code=end

