/*
 * @lc app=leetcode.cn id=36 lang=java
 *
 * [36] 有效的数独
 */

// @lc code=start
class Solution {
    public boolean isValidSudoku(char[][] board) {
        boolean[][] checker = new boolean[27][10];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == '.') {
                    continue;
                }
                int digit = board[i][j] - '0';
                int[] zones = {i, 9 + j, 18 + (i / 3) * 3 + (j / 3)};
                for (int zone : zones) {
                    if (checker[zone][digit]) {
                        return false;
                    }
                    checker[zone][digit] = true;
                }
            }
        }
        return true;
    }
}
// @lc code=end

