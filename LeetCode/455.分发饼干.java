/*
 * @lc app=leetcode.cn id=455 lang=java
 *
 * [455] 分发饼干
 */

// @lc code=start
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g); Arrays.sort(s);
        int i = 0;
        for (int j = 0; j < s.length && i < g.length; ++j) {
            if (g[i] <= s[j]) {
                ++i;
            }
        }
        return i;
    }
}
// @lc code=end

