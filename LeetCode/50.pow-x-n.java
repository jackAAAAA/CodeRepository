/*
 * @lc app=leetcode.cn id=50 lang=java
 *
 * [50] Pow(x, n)
 */

// @lc code=start
class Solution {
    public double myPow(double x, int n) {
        long N = n;
        return N >= 0 ? caculate(x, N) : 1 / caculate(x, -N);
    }
    private double caculate(double x, long N) {
        if (N == 0) {
            return 1.0;
        }
        double y = caculate(x, N >> 1);
        return (N & 1) == 0 ? y * y : y * y * x;
    }
}
// @lc code=end
