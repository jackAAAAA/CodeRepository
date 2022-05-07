/*
 * @lc app=leetcode.cn id=42 lang=java
 *
 * [42] 接雨水
 */

// @lc code=start
class Solution {
    public int trap(int[] height) {
        int len = height.length;
        if (len == 0) {
            return 0;
        }
        int[] left_max = new int[len];
        int[] right_max = new int[len];
        left_max[0] = height[0]; right_max[len - 1] = height[len - 1];
        for (int i = 1; i < len; ++i) {
            left_max[i] = Math.max(left_max[i - 1], height[i]);
        }
        for (int j = len - 2; j >= 0; --j) {
            right_max[j] = Math.max(right_max[j + 1], height[j]);
        }
        int ans = 0;
        for (int k = 0; k < len; ++k) {
            ans += Math.min(right_max[k], left_max[k]) - height[k];
        }
        return ans;
    }
}
// @lc code=end

