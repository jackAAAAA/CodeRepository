/*
 * @lc app=leetcode.cn id=8 lang=java
 *
 * [8] 字符串转换整数 (atoi)
 */

// @lc code=start
class Solution {
    public int myAtoi(String s) {
        char[] ca = s.toCharArray();
        int i = 0, sign = 1, base = 0, len = ca.length, MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
        while (i < len && ca[i] == ' ') {
            ++i;
        }
        if (i == len) {
            return 0;
        }
        if (i < len && (ca[i] == '+' || ca[i] == '-')) {
            if (ca[i] == '-') {
                sign = -1;
            }
            ++i;
        }
        while (i < len && Character.isDigit(ca[i])) {
            if (base > MAX / 10 || (base == MAX / 10 && ca[i] > '7')) {
                if (sign == -1) {
                    return MIN;
                }
                return MAX;
            }
            base = 10 * base + (ca[i++] - '0');
        }
        return sign * base;
    }
}
// @lc code=end

