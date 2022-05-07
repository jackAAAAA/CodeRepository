/*
 * @lc app=leetcode.cn id=221 lang=java
 *
 * [221] 最大正方形
 */

// @lc code=start
class Solution {
    public int maximalSquare(char[][] matrix) {
        int row = matrix.length, col = matrix[0].length, max = 0;
        int[] dp = new int[col + 1];
        for (int i = 0; i < row; ++i) {
            int northwest = 0;
            for (int j = 0; j < col; ++j) {
                int nextnorthwest = dp[j + 1];
                if (matrix[i][j] == '1') {
                    dp[j + 1] = Math.min(Math.min(dp[j], dp[j + 1]), northwest) + 1;
                    max = Math.max(max, dp[j + 1]);
                } else {
                    dp[j + 1] = 0;
                }
                northwest = nextnorthwest;
            }
        }
        return max * max;
    }
}
// @lc code=end

