/*
 * @lc app=leetcode.cn id=309 lang=java
 *
 * [309] 最佳买卖股票时机含冷冻期
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len == 1) {
            return 0;
        }
        int dp0 = 0, dp1 = -prices[0], dp2 = 0;
        for (int i = 1; i < len; ++i) {
            int newdp0 = Math.max(dp0, dp2);
            int newdp1 = Math.max(dp1, dp0 - prices[i]);
            int newdp2 = dp1 + prices[i];
            dp0 = newdp0; dp1 = newdp1; dp2 = newdp2;
        }
        return Math.max(dp0, dp2);
    }
}
// @lc code=end

