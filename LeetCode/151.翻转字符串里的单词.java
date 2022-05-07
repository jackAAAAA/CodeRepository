/*
 * @lc app=leetcode.cn id=151 lang=java
 *
 * [151] 翻转字符串里的单词
 */

// @lc code=start
class Solution {
    public String reverseWords(String s) {
        int end = s.length() - 1;
        StringBuilder sb = new StringBuilder();
        while (end >= 0) {
            while (end >= 0 && s.charAt(end) == ' ') {
                --end;
            }
            int start = end;
            while (start >= 0 && s.charAt(start) != ' ') {
                --start;
            }
            if (start != end) {
                sb.append(s.substring(start + 1, end + 1)).append(" ");
            }
            end = start - 1;
        }
        return sb.substring(0, sb.length() - 1).toString();
    }
}
// @lc code=end

