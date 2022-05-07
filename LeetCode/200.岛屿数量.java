/*
 * @lc app=leetcode.cn id=200 lang=java
 *
 * [200] 岛屿数量
 */

// @lc code=start
class Solution {
    public int numIslands(char[][] grid) {
        int row = grid.length, col = grid[0].length, ans = 0;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (grid[i][j] == '1') {
                    dfs_mark(grid, row, col, i, j);
                    ++ans;
                }
            }
        }
        return ans;
    }
    private void dfs_mark(char[][] grid, int row, int col, int i, int j) {
        if (i < 0 || i == row || j < 0 || j == col || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '2';
        dfs_mark(grid, row, col, i, j + 1);
        dfs_mark(grid, row, col, i + 1, j);
        dfs_mark(grid, row, col, i, j - 1);
        dfs_mark(grid, row, col, i - 1, j);
    }
}
// @lc code=end

