public class Homework_week6 {

    //minPathSum
    public int minPathSum(int[][] grid) {
        int len = grid[0].length;
        int[] dp = new int[len];
        dp[0] = grid[0][0];
        //首先处理第一行
        for (int i = 1; i < len; ++i) {
            dp[i] = dp[i - 1] + grid[0][i];
        }
        //处理第二行到最后一行
        for (int i = 1; i < grid.length; ++i) {
            //处理该行的第一个元素
            dp[0] = dp[0] + grid[i][0];
            //处理该行其他其他元素
            for (int j = 1; j < len; ++j) {
                //dp[i-1]：代表grid[i][j]左边元素的dp值；dp[i]：代表grid[i][j]上边元素的dp值
                dp[j] = Math.min(dp[j - 1] + grid[i][j], dp[i] + grid[i][j]);
            }
        }
        //dp[len-1]代表的是：到达grid二维数组最后一个元素的最小移动步数
        return dp[len - 1];
    }

    //numDecodings
    //method_1
    private int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        int[] dp = new int[len];
        dp[0] = s.charAt(0) != '0' ? 1 : 0;
        for (int i = 1; i < len; ++i) {
            int first = Integer.valueOf(s.substring(i, i + 1));
            int second = Integer.valueOf(s.substring(i - 1, i + 1));
            if (first >= 1 && first <= 9) {
                dp[i] += dp[i - 1];
            }
            if (second >= 10 && second <= 26) {
                dp[i] += i >= 2 ? dp[i - 2] : 1;
            }
        }
        return dp[len - 1];
    }

    //method_2
    private int numDecodings_1(String s) {
        int dp1 = 1, dp2 = 0, n = s.length();
        for (int i = n - 1; i >= 0; i--) {
            int dp = s.charAt(i) == '0' ? 0 : dp1;
            if (i < n - 1 && (s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) < '7'))
                dp += dp2;
            dp2 = dp1;
            dp1 = dp;
        }
        return dp1;
    }

}
