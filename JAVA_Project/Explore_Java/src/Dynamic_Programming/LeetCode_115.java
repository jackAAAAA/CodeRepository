package Dynamic_Programming;

import java.util.Arrays;

public class LeetCode_115 {
    public static void main(String[] args) {
        String s = "rabbbit";
        String t = "rabbit";
        String s1 = "adbdadeecadeadeccaeaabdabdbcdabddddabcaaadbabaaedeeddeaeebcdeabcaaaeeaeeabcddcebddebeebedaecccbdcbcedbdaeaedcdebeecdaaedaacadbdccabddaddacdddc";
        String t1 = "bcddceeeebecbc";
        int ans1 = numDistinct(s1, t1);
//        int ans2 = numDistinct_2(s1, t1);
        System.out.println("The answer is: " + ans1);
//        System.out.println("The answer is: " + ans2);
//        700531452
    }
    public static int numDistinct(String s, String t) {
        int sl = s.length();
        int tl = t.length();
        int[] dp = new int[tl+1];
        dp[tl] = 1;
        for (int i = sl-1; i >= 0; --i){
            for (int j = 0; j < tl; ++j){
                if (t.charAt(j) == s.charAt(i)){
                    dp[j] += dp[j+1];
                }
            }
        }
        return dp[0];
    }

    public static int numDistinct_2(String s, String t) {
        char[] ca_s = s.toCharArray(), ca_t = t.toCharArray();
        int sl = ca_s.length, tl = ca_t.length;
        if (sl < tl) {
            return 0;
        }
        int[][] memo = new int[sl + 1][tl + 1];
        //二维数组赋初始值
        for (int[] e : memo) {
            Arrays.fill(e, -1);
        }
        return helper(memo, ca_s, sl, ca_t, tl);
    }

    public static int helper(int[][] memo, char[] ca_s, int i, char[] ca_t, int j) {
        if (j == 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        } else {
            if (ca_s[i - 1] == ca_t[j - 1]) {
                return helper(memo, ca_s, i - 1, ca_t, j - 1) + helper(memo, ca_s, i - 1, ca_t, j);
            } else {
                return helper(memo, ca_s, i - 1, ca_t, j);
            }
        }
    }

}
