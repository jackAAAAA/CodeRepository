/*
 * @lc app=leetcode.cn id=37 lang=java
 *
 * [37] 解数独
 */

// @lc code=start
class Solution {
    private boolean valid;
    public void solveSudoku(char[][] board) {
        valid = false;
        boolean[][] checker = new boolean[27][10];
        List<int[]> spaces = new ArrayList<>();
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == '.') {
                    spaces.add(new int[] {i, j});
                } else {
                    int digit = board[i][j] - '0';
                    int[] zones = {i, 9 + j, 18 + (i / 3) * 3 + (j / 3)};
                    for (int z : zones) {
                        checker[z][digit] = true;
                    }
                }
            }
        }
        backtrack(board, checker, spaces, 0);
    }
    public void backtrack(char[][] board, boolean[][] checker, List<int[]> spaces, int pos) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1];
        int[] z = {i, 9 + j, 18 + (i / 3) * 3 + (j / 3)};
        for (int digit = 1; digit <= 9 && !valid; ++digit) {
            if (!checker[z[0]][digit] && !checker[z[1]][digit] && !checker[z[2]][digit]) {
                for (int mem : z) {
                    checker[mem][digit] = true;
                }
                board[i][j] = (char)(digit + '0');
                backtrack(board, checker, spaces, pos + 1);
                for (int mem : z) {
                    checker[mem][digit] = false;
                }
            }
        }
    }
}
// @lc code=end

