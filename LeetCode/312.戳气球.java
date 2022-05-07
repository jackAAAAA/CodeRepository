/*
 * @lc app=leetcode.cn id=312 lang=java
 *
 * [312] 戳气球
 */

// @lc code=start
class Solution {
    public int maxCoins(int[] nums) {
        int len = nums.length;
        int[] num1 = new int[len + 2];
        num1[0] = 1; num1[len + 1] = 1;
        System.arraycopy(nums, 0, num1, 1, len);
        len += 2; nums = num1;
        int[][] dp = new int[len][len];
        for (int i = len - 3; i >= 0; --i) {
            for (int j = i + 2; j < len; ++j) {
                int max = 0;
                for (int k = i + 1; k < j; ++k) {
                    int temp = dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[j];
                    if (temp > max) {
                        max = temp;
                    }
                }
                dp[i][j] = max;
            }
        }
        return dp[0][len - 1];
    }
}
// @lc code=end

