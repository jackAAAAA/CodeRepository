/*
 * @lc app=leetcode.cn id=51 lang=java
 *
 * [51] N 皇后
 */

// @lc code=start
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        backtrack(n, new int[n], res, 0, 0, 0, 0);
        return res;
    }
    public void backtrack(int n, int[] queens, List<List<String>> res, int row, int col, int ld, int rd) {
        if (row == n) {
            List<String> board = generateBoard(queens, n);
            res.add(board);
            return;
        }
        int bits = ~(col | ld | rd) & ((1 << n) - 1);
        while (bits > 0) {
            int pick = bits & (-bits);
            int column = Integer.bitCount(pick - 1);
            queens[row] = column;
            backtrack(n, queens, res, row + 1, col | pick, (ld | pick) << 1, (rd | pick) >> 1);
            queens[row] = 0;
            bits &= (bits - 1);
        }
    }
    public List<String> generateBoard(int[] queens, int n) {
        char[] row = new char[n];
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            list.add(String.valueOf(row));
        }
        return list;
    }
}
// @lc code=end

