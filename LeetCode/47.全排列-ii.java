/*
 * @lc app=leetcode.cn id=47 lang=java
 *
 * [47] 全排列 II
 */

// @lc code=start

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
        return list;
    }
    private void backtrack(List<List<Integer>> list, List<Integer> templist, int[] nums, boolean[] visited) {
        if (templist.size() == nums.length) {
            list.add(new ArrayList<>(templist));
            return;
        }
        for (int i = 0; i < nums.length; ++i) {
            if (visited[i] || (i > 0 && !visited[i - 1] && nums[i] == nums[i - 1])) {
                continue;
            }
            templist.add(nums[i]);
            visited[i] = true;
            backtrack(list, templist, nums, visited);
            visited[i] = false;
            templist.remove(templist.size() - 1);
        }
    }
}
// @lc code=end

