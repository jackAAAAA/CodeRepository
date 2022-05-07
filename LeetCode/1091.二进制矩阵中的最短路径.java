/*
 * @lc app=leetcode.cn id=1091 lang=java
 *
 * [1091] 二进制矩阵中的最短路径
 */

// @lc code=start
class Solution {
    private int m, n;
    public int shortestPathBinaryMatrix (int[][] grid) {
        //TO DO
        m = grid.length; n = grid[0].length;
        if (grid[0][0] == 1 || grid[m - 1][n - 1] == 1) {
            return -1;
        }
        int[][] dir = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, -1}, {-1, 1}
        };
        grid[0][0] = 1;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[] {0, 0});
        while (!queue.isEmpty() && grid[m - 1][n - 1] == 0) {
            int[] xy = queue.poll();
            int preLength = grid[xy[0]][xy[1]];
            for (int i = 0; i < 8; ++i) {
                int newX = xy[0] + dir[i][0];
                int newY = xy[1] + dir[i][1];
                while (inGrid(newX, newY) && grid[newX][newY] == 0) {
                    grid[newX][newY] = preLength + 1;
                    queue.offer(new int[] {newX, newY});
                }
            }

        }
        return grid[m - 1][n - 1] == 0 ? -1 : grid[m - 1][n - 1];
    }
    private boolean inGrid(int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }
}
// @lc code=end

