/*
 * @lc app=leetcode.cn id=130 lang=java
 *
 * [130] 被围绕的区域
 */

// @lc code=start
class Solution {
    public void solve(char[][] board) {
        int row = board.length, col = board[0].length;
        for (int i = 0; i < row; ++i) {
            dfs(board, row, col, i, 0);
            dfs(board, row, col, i, col - 1);
        }
        for (int j = 1; j < col - 1; ++j) {
            dfs(board, row, col, 0, j);
            dfs(board, row, col, row - 1, j);
        }
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }
    public void dfs(char[][] board, int row, int col, int i, int j) {
        if (board[i][j] != 'O') {
            return;
        }
        board[i][j] = '#';
        if (j < col - 1) {
            dfs(board, row, col, i, j + 1);
        }
        if (i < row - 1) {
            dfs(board, row, col, i + 1, j);
        }
        if (j > 0) {
            dfs(board, row, col, i, j - 1);
        }
        if (i > 0) {
            dfs(board, row, col, i - 1, j);
        }
    }
}
// @lc code=end

