/*
 * @lc app=leetcode.cn id=55 lang=java
 *
 * [55] 跳跃游戏
 */

// @lc code=start
class Solution {
    public boolean canJump(int[] nums) {
        //顺推
        // int len = nums.length, reach = 0, i = 0;
        // while (i <= reach && i < len) {
        //     reach = Math.max(reach, i + nums[i++]);
        // }
        // return i == len;
        //逆推
        int len = nums.length, reach = len - 1;
        for (int i = len - 2; i >= 0; --i) {
            if (i + nums[i] >= reach) {
                reach = i;
            }
        }
        return reach == 0;
    }
}
// @lc code=end

