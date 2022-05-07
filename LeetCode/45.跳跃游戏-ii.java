/*
 * @lc app=leetcode.cn id=45 lang=java
 *
 * [45] 跳跃游戏 II
 */

// @lc code=start
class Solution {
    public int jump(int[] nums) {
        //顺推-不断迭代
        int len = nums.length, reach = 0, end = 0, min = 0;
        for (int i = 0; i < len - 1; ++i) {
            reach = Math.max(reach, i + nums[i]);
            if (i == end) {
                ++min;
                end = reach;
            }
        }
        return min;

        // 逆推-以终为始
        // int len = nums.length, reach = len - 1, min = 0;
        // while (reach > 0) {
        //     for (int i = 0; i < reach; ++i) {
        //         if (i + nums[i] >= reach) {
        //             ++min;
        //             reach = i;
        //             break;
        //         }
        //     }
        // }
        // return min;
    }
}
// @lc code=end

