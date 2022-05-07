/*
 * @lc app=leetcode.cn id=33 lang=java
 *
 * [33] 搜索旋转排序数组
 */

// @lc code=start
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int num = (nums[mid] < nums[0]) == (target < nums[0]) ? nums[mid] : (target < nums[0]) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            if (num == target) {
                return mid;
            } else if (target < num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
// @lc code=end

