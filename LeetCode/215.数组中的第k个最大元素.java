/*
 * @lc app=leetcode.cn id=215 lang=java
 *
 * [215] 数组中的第K个最大元素
 */

// @lc code=start
class Solution {
    
    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        if (len < k) return 0;
        else {
            return quickSelect(nums, 0, len - 1, len - k);
        }
    }

    private static int quickSelect(int[] nums, int begin, int end, int k) {
        if (end <= begin) return nums[begin];
        int pivot = partition(nums, begin, end);
        if (k == pivot) {
            return nums[pivot];
        } else if (k < pivot) {
            return quickSelect(nums, begin, pivot - 1, k);
        } else {
            return quickSelect(nums, pivot + 1, end, k);
        }
    }

    private static int partition(int[] nums, int begin, int end) {
        int pivot = end, counter = begin;
        for (int i = begin; i < end; ++i) {
            if (nums[i] < nums[pivot]) {
                if (i != counter) swap(nums, counter++, i);
                else ++counter;
            }
        }
        swap(nums, pivot, counter);
        return counter;
    }
    
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // private static int partition(int[] nums, int begin, int end) {
    //     // int pivot = nums[end];
    //     int pivot = nums[(int) Math.random() * (end - begin + 1) + begin];
    //     int i = begin, j = end;
    //     while (i < j) {
    //         while (nums[i] < pivot) ++i;
    //         while (nums[j] > pivot) --j;
    //         if (i < j) {
    //             if (nums[i] != nums[j]) swap(nums, i, j);
    //             else ++i;
    //         }
    //     }
    //     return i;
    // }

    // private static void swap(int[] nums, int i, int j) {
    //     int temp = nums[i];
    //     nums[i] = nums[j];
    //     nums[j] = temp;
    // }

}
// @lc code=end

