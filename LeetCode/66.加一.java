/*
 * @lc app=leetcode.cn id=66 lang=java
 *
 * [66] 加一
 */

// @lc code=start
class Solution {
    public int[] plusOne(int[] nums) {
        int len = nums.length;
        for (int i = len - 1; i >= 0; --i) {
            if (nums[i] < 9) {
                ++nums[i];
                return nums;
            } else {
                nums[i] = 0;
            }
        }
        int[] newnumber = new int[len + 1];
        newnumber[0] = 1;
        return newnumber;
    }
}
// @lc code=end

