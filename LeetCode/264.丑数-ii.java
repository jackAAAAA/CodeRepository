/*
 * @lc app=leetcode.cn id=264 lang=java
 *
 * [264] 丑数 II
 */

// @lc code=start
class Solution {

    public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        ugly[0] = 1;
        int index2 = 0, index3 = 0, index5 = 0;
        for (int i = 1; i < n; ++i) {
            ugly[i] = Math.min(ugly[index2] * 2, Math.min(ugly[index3] * 3, ugly[index5] * 5));
            if (ugly[i] == ugly[index2] * 2) {
                ++index2;
            }
            if (ugly[i] == ugly[index3] * 3) {
                ++index3;
            }
            if (ugly[i] == ugly[index5] * 5) {
                ++index5;
            }
        }
        return ugly[n - 1];
    }
    
}
// @lc code=end

