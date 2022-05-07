/*
 * @lc app=leetcode.cn id=52 lang=java
 *
 * [52] N皇后 II
 */

// @lc code=start
class Solution {
    private int count;
    public int totalNQueens(int n) {
        count = 0;
        backtrack(n, 0, 0, 0, 0);
        return count;
    }
    public void backtrack(int n, int row, int col, int ld, int rd) {
        if (row == n) {
            ++count;
            return;
        }
        int bits = ~(col | ld | rd) & ((1 << n) - 1);
        while (bits != 0) {
            int pick = bits & (-bits);
            backtrack(n, row + 1, col | pick, (ld | pick) << 1, (rd | pick) >> 1);
            bits &= (bits - 1);
        }
    }
}
// @lc code=end

