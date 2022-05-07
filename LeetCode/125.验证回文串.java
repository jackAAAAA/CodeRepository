/*
 * @lc app=leetcode.cn id=125 lang=java
 *
 * [125] 验证回文串
 */

// @lc code=start
class Solution {
    public boolean isPalindrome(String s) {
        char[] ca = s.toUpperCase().toCharArray();
        int len = ca.length;
        int i = 0, j = len - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(ca[i])) {
                ++i;
            }
            while (i < j && !Character.isLetterOrDigit(ca[j])) {
                --j;
            }
            if (ca[i] != ca[j]) {
                return false;
            }
            ++i; --j;
        }
        return true;
    }
}
// @lc code=end

