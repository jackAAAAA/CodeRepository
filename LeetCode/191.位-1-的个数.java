/*
 * @lc app=leetcode.cn id=191 lang=java
 *
 * [191] 位1的个数
 */

// @lc code=start
class Solution {
    public int hammingWeight(int n) {
        int cnt = 0;
        while (n != 0) {
            n &= (n - 1);
            ++cnt;
        }
        return cnt;
    }
}
// @lc code=end

