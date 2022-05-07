/*
 * @lc app=leetcode.cn id=122 lang=java
 *
 * [122] 买卖股票的最佳时机 II
 */

// @lc code=start
class Solution {
    public int maxProfit(int[] prices) {
        int dp0 = 0, dp1 = -prices[0];
        for (int price : prices) {
            int newdp0 = Math.max(dp0, dp1 + price);
            int newdp1 = Math.max(dp1, dp0 - price);
            dp0 = newdp0; dp1 = newdp1;
        }
        return dp0;
    }
}
// @lc code=end

