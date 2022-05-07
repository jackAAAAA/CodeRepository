/*
 * @lc app=leetcode.cn id=403 lang=java
 *
 * [403] 青蛙过河
 */

// @lc code=start
class Solution {
    public boolean canCross(int[] stones) {
        int len = stones.length;
        boolean[][] dp = new boolean[len][len];
        dp[0][1] = true;
        for (int i = 1; i < len; ++i) {
            for (int j = 0; j < i; ++j) {
                int dist = stones[i] - stones[j];
                if (dist >= len || !dp[j][dist]) {
                    continue;
                }
                if (i == len - 1) {
                    return true;
                }
                if (dist > 0) {
                    dp[i][dist - 1] = true;
                }
                dp[i][dist] = true;
                if (dist < len - 1) {
                    dp[i][dist + 1] = true;
                }
            }
        }
        return false;
    }
}
// @lc code=end

