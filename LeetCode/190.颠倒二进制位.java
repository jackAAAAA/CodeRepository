/*
 * @lc app=leetcode.cn id=190 lang=java
 *
 * [191] 位1的个数
 */

// @lc code=start
class Solution {
    public int reverseBits(int n) {
        int ans = 0;
        for (int i = 0; i < 32; ++i) {
            if ((n & 1) == 1) {
                ans |= (1 << (31 - i));
            }
            n >>= 1;
        }
        return ans;
    }
}
// @lc code=end

