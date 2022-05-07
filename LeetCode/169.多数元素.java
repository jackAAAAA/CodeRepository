/*
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 */

// @lc code=start
class Solution {
    public int majorityElement(int[] nums) {
        int len = nums.length;
        int count = 1, candidate = nums[0];
        for (int i = 1; i < len; ++i) {
            //Replace the candidate
            if (count == 0) {
                candidate = nums[i];
            }
            //Statistics
            count += (candidate == nums[i]) ? 1 : -1;
        }
        return candidate;
    }
}
// @lc code=end

