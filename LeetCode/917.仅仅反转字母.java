/*
 * @lc app=leetcode.cn id=917 lang=java
 *
 * [917] 仅仅反转字母
 */

// @lc code=start
class Solution {
    public String reverseOnlyLetters(String s) {
        char[] ca = s.toCharArray();
        int len = ca.length;
        int i = 0, j = len - 1;
        while (i < j) {
            while (i < j && !Character.isLetter(ca[i])) {
                ++i;
            }
            while (i < j && !Character.isLetter(ca[j])) {
                --j;
            }
            if (i < j) {
                char temp = ca[i];
                ca[i++] = ca[j];
                ca[j--] = temp;
            }
        }
        return String.valueOf(ca);
    }
}
// @lc code=end

