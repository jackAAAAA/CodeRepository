/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 */

// @lc code=start
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int onestep_before = 2, twosteps_before = 1;
        int all_ways = 0;
        for (int i = 3; i <= n; ++i) {
            all_ways = onestep_before + twosteps_before;
            twosteps_before = onestep_before;
            onestep_before = all_ways;
        }
        return all_ways;
    }
}
// @lc code=end

