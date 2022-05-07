/*
 * @lc app=leetcode.cn id=557 lang=java
 *
 * [557] 反转字符串中的单词 III
 */

// @lc code=start
class Solution {
    public String reverseWords(String s) {
        char[] ca = s.toCharArray();
        int len = ca.length;
        int start = 0;
        for (int i = 0; i < len; ++i) {
            if (ca[i] == ' ' || i + 1 == len) {
                int end = i + 1 == len ? i : i - 1;
                swap(ca, start, end);
                start = i + 1;
            }
        }
        return String.valueOf(ca);
    }
    public void swap(char[] ca, int i, int j) {
        while (i < j) {
            char temp = ca[i];
            ca[i++] = ca[j];
            ca[j--] = temp;
        }
    }
}
// @lc code=end

