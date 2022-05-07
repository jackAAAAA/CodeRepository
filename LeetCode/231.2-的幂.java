/*
 * @lc app=leetcode.cn id=231 lang=java
 *
 * [231] 2的幂
 */

// @lc code=start
class Solution {
    public boolean isPowerOfTwo(int n) {
        return n <= 0 ? false : (n & (-n)) == n;
    }
}
// @lc code=end

