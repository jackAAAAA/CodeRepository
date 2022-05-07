/*
 * @lc app=leetcode.cn id=44 lang=java
 *
 * [44] 通配符匹配
 */

// @lc code=start
class Solution {
    public boolean isMatch(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int i = 0, j = 0, iStar = -1, jStar = -1, sl = cs.length, tl = ct.length;
        while (i < sl) {
            if (j < tl && (cs[i] == ct[j] || ct[j] == '?')) {
                ++i; ++j;
            } else if (j < tl && ct[j] == '*') {
                iStar = i;
                jStar = j++;
            } else if (iStar >= 0) {
                i = ++iStar;
                j = jStar + 1;
            } else {
                return false;
            }
        }
        while (j < tl && ct[j] == '*') {
            ++j;
        }
        return j == tl;
    }
}
// @lc code=end

