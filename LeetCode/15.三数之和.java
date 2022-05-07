/*
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        int len = nums.length;
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (nums == null || len == 0) {
            return res;
        }

        Arrays.sort(nums);
        for (int i = 0; i < len; ++i) {
            if (nums[0] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = len - 1, target = -nums[i];
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left + 1] == nums[left]) {
                        ++left;
                    }
                    while (left < right && nums[right - 1] == nums[right]) {
                        --right;
                    }
                    ++left; --right;
                } else if (nums[left] + nums[right] < target) {
                    ++left;
                } else {
                    --right;
                }
            }
        }
        return res;

    }
}
// @lc code=end

