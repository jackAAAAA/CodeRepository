/*
 * @lc app=leetcode.cn id=39 lang=java
 *
 * [39] 组合总和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), candidates, target, 0);
        return list;
    }
    private void backtrack(List<List<Integer>> list, List<Integer> templist, int[] candidates, int target, int start) {
        if (target < 0) {
            return;
        } else if (target == 0) {
            list.add(new ArrayList<>(templist));
            return;
        } else {
            for (int i = start; i < candidates.length; ++i) {
                templist.add(candidates[i]);
                backtrack(list, templist, candidates, target - candidates[i], i);
                templist.remove(templist.size() - 1);
            }
        }
    }
}
// @lc code=end

