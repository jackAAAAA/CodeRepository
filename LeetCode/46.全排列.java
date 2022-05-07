/*
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 */

// @lc code=start
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
        return list;
    }
    private void backtrack(List<List<Integer>> list, List<Integer> templist, int[] nums, boolean[] visited) {
        if (templist.size() == nums.length) {
            list.add(new ArrayList<>(templist));
            return;
        } else {
            for (int i = 0; i < nums.length; ++i) {
                if (visited[i]) {
                    continue;
                }
                visited[i] = true;
                templist.add(nums[i]);
                backtrack(list, templist, nums, visited);
                templist.remove(templist.size() - 1);
                visited[i] = false;
            }
        }
    }
}
// @lc code=end

