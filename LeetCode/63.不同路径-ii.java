/*
 * @lc app=leetcode.cn id=63 lang=java
 *
 * [63] 不同路径 II
 */

// @lc code=start
class Solution {
    public int uniquePathsWithObstacles(int[][] grid) {
        int row = grid.length, col = grid[0].length;
        if (grid[0][0] == 1 || grid[row - 1][col - 1] == 1) {
            return 0;
        }
        int[] dp = new int[col];
        dp[0] = 1;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (grid[i][j] == 1) {
                    dp[j] = 0;
                    continue;
                } else if (j >= 1) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return dp[col - 1];
    }
}
// @lc code=end
