/*
 * @lc app=leetcode.cn id=714 lang=java
 *
 * [714] 买卖股票的最佳时机含手续费
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }
        int dp0 = 0, dp1 = -prices[0];
        for (int i = 1; i < len; ++i) {
            int newdp0 = Math.max(dp0, dp1 + prices[i] - fee);
            int newdp1 = Math.max(dp1, dp0 - prices[i]);
            dp0 = newdp0; dp1 = newdp1;
        }
        return dp0;
    }
}
// @lc code=end

