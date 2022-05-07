/*
 * @lc app=leetcode.cn id=363 lang=java
 *
 * [363] 矩形区域不超过 K 的最大数值和
 */

// @lc code=start
class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int row = matrix.length, col = matrix[0].length, res = Integer.MIN_VALUE;
        int[] sum = new int[row];
        for (int j = 0; j < col; ++j) {
            Arrays.fill(sum, 0);
            for (int i = j; i < col; ++i) {
                for (int r = 0; r < row; ++r) {
                    sum[r] += matrix[r][i];
                }
                int cur = Integer.MIN_VALUE, max = sum[0];
                for (int mem : sum) {
                    cur = Math.max(mem, mem + cur);
                    max = Math.max(max, cur);
                    if (max == k) {
                        return max;
                    }
                }
                if (max < k) {
                    res = Math.max(res, max);
                } else {
                    for (int a = 0; a < row; ++a) {
                        int curSum = 0;
                        for (int b = a; b < row; ++b) {
                            curSum += sum[b];
                            if (curSum == k) {
                                return curSum;
                            } else if (curSum < k) {
                                res = Math.max(res, curSum);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
}
// @lc code=end

