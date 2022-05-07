/*
 * @lc app=leetcode.cn id=300 lang=java
 *
 * [300] 最长递增子序列
 */

// @lc code=start
class Solution {
    public int lengthOfLIS(int[] nums) {
        int index = 1, len = nums.length;
        int[] ans = new int[len + 1];
        ans[index] = nums[0];
        for (int i = 1; i < len; ++i) {
            if (nums[i] > ans[index]) {
                ans[++index] = nums[i];
            } else {
                int left = 1, right = index, pos = 0;
                while (left <= right) {
                    int mid = left + ((right - left) >> 1);
                    if (nums[i] > ans[mid]) {
                        pos = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                ans[pos + 1] = nums[i];
            }
        }
        return index;
    }
}
// @lc code=end

