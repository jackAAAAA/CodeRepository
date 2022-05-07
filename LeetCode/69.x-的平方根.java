/*
 * @lc app=leetcode.cn id=69 lang=java
 *
 * [69] x 的平方根
 */

// @lc code=start
class Solution {
    public int mySqrt(int x) {
        if (x == 1) {
            return x;
        }
        long N = x;
        while (N * N > x) {
            N = (N + x / N) >> 1;
        }
        return (int) N;
    }
}
// @lc code=end

