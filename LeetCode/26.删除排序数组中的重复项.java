/*
 * @lc app=leetcode.cn id=26 lang=java
 *
 * [26] 删除排序数组中的重复项
 */

// @lc code=start
class Solution {
    public int removeDuplicates(int[] nums) {

        int i = 0, j = 1;
        int len = nums.length;
        if (nums == null || len == 0) {
            return 0;
        }

        for (; j < len; ++j) {
            if (nums[j] != nums[i]) {
                // swap(nums, ++i, j);
                if (j - i > 1) {
                    nums[++i] = nums[j];
                } else {
                    ++i;
                }
            }
        }
        return ++i;

    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
// @lc code=end

