/*
 * @lc app=leetcode.cn id=541 lang=java
 *
 * [541] 反转字符串 II
 */

// @lc code=start
class Solution {
    public String reverseStr(String s, int k) {
        char[] ca = s.toCharArray();
        int len = ca.length;
        for (int start = 0; start < len; start += 2 * k) {
            int end = Math.min(start + k - 1, len - 1);
            swap(ca, start, end);
        }
        return String.valueOf(ca);
    }
    public void swap(char[] ca, int i, int j) {
        while (i < j) {
            char c = ca[i];
            ca[i++] = ca[j];
            ca[j--] = c;
        }
    }
}
// @lc code=end

