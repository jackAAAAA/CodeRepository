package Array;

import java.util.Arrays;

public class LeetCode_63 {

    public static void main(String[] args) {
        int[][] obstacles = {{0, 1, 0},{0, 1, 0},{0, 0, 0}};
//        int paths = uniquePathsWithObstacles(obstacles);
        int paths = uniqueP2(obstacles);
        System.out.println(paths);
    }

    private static int[] f = new int[3];

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;

         f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;
//        Arrays.fill(f, 1);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                if (j - 1 >= 0) {
                    //f[j]:从上往下走；f[j - 1]:从左往右走
                    f[j] += f[j - 1];
                }
                System.out.print("i = " + i + " f[j] = " + f[j] + " ");
            }
        }
        return f[n - 1];
    }

    public static int uniquePaths(int[][] obstacles) {
        int m = obstacles.length, n = obstacles[0].length;
        f[n- 1] = obstacles[m - 1][n - 1] == 0 ? 1 : 0;
        for (int i = m - 1; i >= 0; --i) {
            for (int j = n - 2; j >= 0; --j) {
                if (obstacles[i][j] == 1) {
                    f[j] = 0;
                    continue;
                } else {
                    f[j] += f[j + 1];
                }
            }
        }
        return f[0];
    }

    public static int uniqueP2(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }

        // 定义 dp 数组并初始化第 1 行和第 1 列。
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n && obstacleGrid[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        // 根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        System.out.println(dp[0][2] + " " + dp[1][2] + " " + dp[2][2]);
        return dp[m - 1][n - 1];
    }

}
