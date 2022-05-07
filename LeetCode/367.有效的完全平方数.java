/*
 * @lc app=leetcode.cn id=367 lang=java
 *
 * [367] 有效的完全平方数
 */

// @lc code=start

class Solution {
    public boolean isPerfectSquare(int num) {
        if (num == 1) {
            return true;
        }
        long N = num;
        while (N * N > num) {
            N = (N + num / N) >> 1;
        }
        return N * N == num;
    }
}
// @lc code=end

