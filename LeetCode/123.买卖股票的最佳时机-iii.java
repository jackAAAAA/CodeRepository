/*
 * @lc app=leetcode.cn id=123 lang=java
 *
 * [123] 买卖股票的最佳时机 III
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len == 1) {
            return 0;
        }
        int tradeNum = Math.min(2, len >> 1);
        int[][][] dp = new int[len][2][tradeNum + 1];
        for (int k = 1; k <= tradeNum; ++k) {
            dp[0][1][k] = -prices[0];
        }
        for (int i = 1; i < len; ++i) {
            for (int k = 1; k <= tradeNum; ++k) {
                dp[i][0][k] = Math.max(dp[i - 1][0][k], dp[i - 1][1][k] + prices[i]);
                dp[i][1][k] = Math.max(dp[i - 1][1][k], dp[i - 1][0][k - 1] - prices[i]);
            }
        }
        return dp[len - 1][0][tradeNum];
    }
}
// @lc code=end

