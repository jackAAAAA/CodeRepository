/*
 * @lc app=leetcode.cn id=5 lang=java
 *
 * [5] 最长回文子串
 */

// @lc code=start
class Solution {
    private int start = 0, maxLen = 0;
    public String longestPalindrome(String s) {
        char[] ca = s.toCharArray();
        int len = ca.length;
        if (len < 2) {
            return s;
        }
        for (int i = 0; i < len - 1; ++i) {
            extendPalindrome(ca, len, i, i);
            extendPalindrome(ca, len, i, i + 1);
        }
        return s.substring(start, start + maxLen);
    }
    public void extendPalindrome(char[] ca, int len, int begin, int end) {
        while (begin >= 0 && end < len && ca[begin] == ca[end]) {
            --begin; ++end;
        }
        if (maxLen < end - begin - 1) {
            start = begin + 1;
            maxLen = end - begin - 1;
        }
    }
}
// @lc code=end

