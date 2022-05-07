/*
 * @lc app=leetcode.cn id=11 lang=java
 *
 * [11] 盛最多水的容器
 */

// @lc code=start
class Solution {
    public int maxArea(int[] height) {

        int left = 0, right = height.length - 1, max = 0;
        while (left < right) {
            max = Math.max(max, (right - left) * (height[left] < height[right] ? height[left++] : height[right--]));
        }
        return max;

    }
}
// @lc code=end

