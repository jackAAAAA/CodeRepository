
/*
 * @lc app=leetcode.cn id=91 lang=java
 *
 * [91] 解码方法
 */

// @lc code=start
class Solution {
    public int numDecodings(String s) {
        char[] ca = s.toCharArray();
        int len = ca.length;
        int res = 1, dp1 = 0, dp2 = 0;
        for (int i = 0; i < len; ++i) {
            dp2 = ca[i] == '0' ? 0 : res;
            if (i > 0 && (ca[i - 1] == '1' || ca[i - 1] == '2' && ca[i] < '7')) {
                dp2 += dp1;
            }
            dp1 = res; res = dp2;
        }
        return res;
    }
}
// @lc code=end

