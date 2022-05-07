/*
 * @lc app=leetcode.cn id=493 lang=java
 *
 * [493] 翻转对
 */

// @lc code=start
class Solution {
    private int count = 0;
    public int reversePairs(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return count;
    }
    public void mergeSort(int[] nums, int left, int right) {
        if (right <= left) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }
    public void merge(int[] nums, int left, int mid, int right) {
        int i = left, j = mid;
        while (i <= mid && j <= right) {
            if (nums[i] > 2 * (long) nums[j]) {
                count += mid - i + 1;
                ++j;
            } else {
                ++i;
            }
        }
        i = left; j = mid;
        int k = 0;
        int[] temp = new int[right - left + 1];
        while (i <= mid && j <= right) {
            temp[k++] = nums[i] < nums[j] ? nums[i++] : nums[j++];
        }
        int start = i, end = mid;
        if (j <= right) {
            start = j; end = right;
        }
        System.arraycopy(nums, start, temp, k, end - start + 1);
        System.arraycopy(temp, 0, nums, left, right - left + 1);
    }
}
// @lc code=end

