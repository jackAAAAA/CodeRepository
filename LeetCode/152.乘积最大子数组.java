/*
 * @lc app=leetcode.cn id=152 lang=java
 *
 * [152] 乘积最大子数组
 */

// @lc code=start
class Solution {
    public int maxProduct(int[] nums) {
        int imin = 1, imax = 1, max = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num < 0) {
                int temp = imax;
                imax = imin;
                imin = temp;
            }
            imin = Math.min(num, imin * num);
            imax = Math.max(num, imax * num);
            max = Math.max(max, imax);
        }
        return max;
    }
}
// @lc code=end

