/*
 * @lc app=leetcode.cn id=818 lang=java
 *
 * [818] 赛车
 */

// @lc code=start
class Solution {
    public int racecar(int target) {
        int[] dp = new int[target + 1];
        return helper(dp, target);
    }
    public int helper(int[] dp, int target) {
        if (dp[target] > 0) {
            return dp[target];
        }
        int k = (int) (Math.log(target) / Math.log(2)) + 1;
        if (target == ((1 << k) - 1)) {
            dp[target] = k;
        } else {
            dp[target] = k + 1 + helper(dp, ((1 << k) - 1 - target));
            for (int back = 0; back < k - 1; ++back) {
                dp[target] = Math.min(dp[target], k - 1 + 1 + back + 1 + helper(dp, target - (1 << (k - 1)) + (1 << back)));
            }
        }
        return dp[target];
    }
}
// @lc code=end

