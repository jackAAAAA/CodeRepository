/*
 * @lc app=leetcode.cn id=1122 lang=java
 *
 * [1122] 数组的相对排序
 */

// @lc code=start
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int max = 0;
        int[] cnt = new int[1200];
        for (int a1 : arr1) {
            max = Math.max(max, a1);
            ++cnt[a1];
        }
        int index = 0;
        for (int a2 : arr2) {
            while (cnt[a2]-- > 0) {
                arr1[index++] = a2;
            }
        }
        for (int i = 0; i <= max; ++i) {
            while (cnt[i]-- > 0) {
                arr1[index++] = i;
            }
        }
        return arr1;
    }
}
// @lc code=end

