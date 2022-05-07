/*
 * @lc app=leetcode.cn id=74 lang=java
 *
 * [74] 搜索二维矩阵
 */

// @lc code=start
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int left = 0, right = m * n - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int num = matrix[mid / n][mid % n];
            if (target == num) {
                return true;
            } else if (target < num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    } 
}
// @lc code=end

