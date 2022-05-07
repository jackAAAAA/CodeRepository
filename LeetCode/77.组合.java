/*
 * @lc app=leetcode.cn id=77 lang=java
 *
 * [77] 组合
 */

// @lc code=start
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        backtrack(list, new ArrayList<Integer>(), n, k, 1);
        return list;
    }
    private void backtrack(List<List<Integer>> list, List<Integer> templist, int n, int k, int start) {
        if (k == 0) {
            list.add(new ArrayList<>(templist));
            return;
        } else {
            for (int i = start; i <= n - k + 1; ++i) {
                templist.add(i);
                backtrack(list, templist, n, k - 1, i + 1);
                templist.remove(templist.size() - 1);
            }
        }
    }
}
// @lc code=end

