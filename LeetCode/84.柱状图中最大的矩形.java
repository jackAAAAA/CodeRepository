/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 */

// @lc code=start
class Solution {
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 1) {
            return heights[0];
        }
        int[] newHeights = new int[len + 2];
        System.arraycopy(heights, 0, newHeights, 1, len);
        len += 2;
        newHeights[0] = newHeights[len - 1] = -1;
        heights = newHeights;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.offerLast(0);
        int ans = 0;
        for (int i = 1; i < len; ++i) {
            while (heights[i] < heights[stack.peekLast()]) {
                int curHeight = heights[stack.pollLast()];
                int curWeight = i - stack.peekLast() - 1;
                ans = Math.max(ans, curHeight * curWeight);
            }
            stack.offerLast(i);
        }
        return ans;
    }
}
// @lc code=end

