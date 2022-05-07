/*
 * @lc app=leetcode.cn id=242 lang=java
 *
 * [242] 有效的字母异位词
 */

// @lc code=start
class Solution {
    public boolean isAnagram(String s, String t) {
        char[] ca_s = s.toCharArray(), ca_t = t.toCharArray();
        int sl = ca_s.length, tl = ca_t.length;
        if (sl != tl) {
            return false;
        }
        char[] alphabet = new char[26];
        for (int i = 0; i < sl; ++i) {
            ++alphabet[ca_s[i] - 'a'];
            --alphabet[ca_t[i] - 'a'];
        }
        for (char c : alphabet) {
            if (c != 0) {
                return false;
            }
        }
        return true;
    }
}
// @lc code=end

