/*
 * @lc app=leetcode.cn id=647 lang=java
 *
 * [647] 回文子串
 */

// @lc code=start
class Solution {
    private int res = 0;
    public int countSubstrings(String s) {
        char[] ca = s.toCharArray();
        int len = ca.length;
        for (int i = 0; i <= len - 1; ++i) {
            extendPalindrome(ca, len, i, i);
            extendPalindrome(ca, len, i, i + 1);
        }
        return res;
    }
    public void extendPalindrome(char[] ca, int len, int begin, int end) {
        while (begin >= 0 && end < len && ca[begin] == ca[end]) {
            --begin; ++end;
            ++res;
        }
    }
}
// @lc code=end

