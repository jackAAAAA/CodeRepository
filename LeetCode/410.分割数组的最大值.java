/*
 * @lc app=leetcode.cn id=410 lang=java
 *
 * [410] 分割数组的最大值
 */

// @lc code=start
class Solution {
    public int splitArray(int[] nums, int m) {
        int len = nums.length, sum = 0, max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }
        if (len == 1 || m == 1) {
            return sum;
        }
        int left = max, right = sum;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            int splits = split(nums, mid);
            if (splits > m) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    public int split(int[] nums, int maxSum) {
        int curSum = 0, splits = 1;
        for (int num : nums) {
            if (curSum + num > maxSum) {
                curSum = num;
                ++splits;
            } else {
                curSum += num;
            }
        }
        return splits;
    }
}
// @lc code=end

