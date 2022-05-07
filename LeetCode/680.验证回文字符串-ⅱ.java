/*
 * @lc app=leetcode.cn id=680 lang=java
 *
 * [680] 验证回文字符串 Ⅱ
 */

// @lc code=start
class Solution {
    public boolean validPalindrome(String s) {
        char[] ca = s.toCharArray();
        int len = ca.length;
        int i = 0, j = len - 1;
        while (i < j) {
            while (i < j && ca[i] == ca[j]) {
                ++i; --j;
            }
            if (i < j) {
                return isPalindrome(ca, len, i + 1, j) || isPalindrome(ca, len, i, j - 1);
            }
        }
        return true;
    }
    public boolean isPalindrome(char[] ca, int len, int begin, int end) {
        while (begin < end) {
            if (ca[begin] != ca[end]) {
                return false;
            }
            ++begin; --end;
        }
        return true;
    }
}
// @lc code=end

