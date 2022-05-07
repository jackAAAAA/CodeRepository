/*
 * @lc app=leetcode.cn id=213 lang=java
 *
 * [213] 打家劫舍 II
 */

// @lc code=start
class Solution {
    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }
        int m1 = myrob(Arrays.copyOfRange(nums, 0, len - 1));
        int m2 = myrob(Arrays.copyOfRange(nums, 1, len));
        return Math.max(m1, m2);
    }
    private int myrob(int[] nums) {
        int pre = 0, cur = 0;
        for (int num : nums) {
            int temp = cur;
            cur = Math.max(cur, pre + num);
            pre = temp;
        }
        return cur;
    }
}
// @lc code=end

