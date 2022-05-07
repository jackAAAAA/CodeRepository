/*
 * @lc app=leetcode.cn id=980 lang=java
 *
 * [980] 不同路径 III
 */

// @lc code=start
class Solution {
    public int uniquePathsIII(int[][] grid) {
        int[][] dire = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int row = grid.length, col = grid[0].length;
        int empty = 0, sx = 0, sy = 0;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (grid[i][j] == 1) {
                    sx = i; sy = j;
                    ++empty;
                } else if (grid[i][j] == 0) {
                    ++empty;
                }
            }
        }
        return backtrack(grid, row, col, empty, sx, sy, dire);
    }
    private int backtrack(int[][] grid, int row, int col, int empty, int sx, int sy, int[][] dire) {
        if (sx < 0 || sx >= row || sy < 0 || sy >= col || grid[sx][sy] == -1) {
            return 0;
        }
        if (grid[sx][sy] == 2) {
            if (empty == 0) {
                return 1;
            } else {
                return 0;
            }
        }
        grid[sx][sy] = -1;
        --empty;
        int res = 0;
        for (int i = 0; i < 4; ++i) {
            res += backtrack(grid, row, col, empty, sx + dire[i][0], sy + dire[i][1], dire);
        }
        grid[sx][sy] = 0;
        ++empty;
        return res;
    }
}
// @lc code=end

