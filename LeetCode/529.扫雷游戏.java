/*
 * @lc app=leetcode.cn id=529 lang=java
 *
 * [529] 扫雷游戏
 */
    
// @lc code=start
class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        int[][] dire = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, -1}, {1, 1}, {-1, -1}, {-1, 1}};
        int row = board.length, col = board[0].length;
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
        } else {
            dfs_mark(board, row, col, x, y, dire);
        }
        return board;
    }
    private void dfs_mark(char[][] board, int row, int col, int x, int y, int[][] dire) {
        int count = 0;
        for (int i = 0; i < 8; ++i) {
            int newX = x + dire[i][0], newY = y + dire[i][1];
            if (inGrid(row, col, newX, newY) && board[newX][newY] == 'M') {
                ++count;
            }
        }
        if (count > 0) {
            board[x][y] = (char)(count + '0');
        } else {
            board[x][y] = 'B';
            for (int i = 0; i < 8; ++i) {
                int newX = x + dire[i][0], newY = y + dire[i][1];
                if (inGrid(row, col, newX, newY) && board[newX][newY] == 'E') {
                    dfs_mark(board, row, col, newX, newY, dire);
                }
            }
        }
    }
    private boolean inGrid(int row, int col, int x, int y) {
        return x >= 0 && x < row && y >= 0 && y < col;
    }
}
// @lc code=end

