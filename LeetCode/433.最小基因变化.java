/*
 * @lc app=leetcode.cn id=433 lang=java
 *
 * [433] 最小基因变化
 */

// @lc code=start
class Solution {
    private int final_min = Integer.MAX_VALUE;
    public int minMutation(String start, String end, String[] bank) {
        backtrack(start, end, bank, 0, new HashSet<>());
        return final_min == Integer.MAX_VALUE ? -1 : final_min;
    }
    public void backtrack(String start, String end, String[] bank, int cur_min, Set<String> visited) {
        if (start.equals(end)) {
            final_min = Math.min(final_min, cur_min);
            return;
        }
        for (String gene : bank) {
            if (!visited.contains(gene)) {
                int diff = 0;
                for (int i = 0; i < gene.length(); ++i) {
                    if (start.charAt(i) != gene.charAt(i)) {
                        ++diff;
                    }
                    if (diff > 1) {
                        break;
                    }
                }
                if (diff == 1) {
                    visited.add(gene);
                    backtrack(gene, end, bank, cur_min + 1, visited);
                    visited.remove(gene);
                }
            }
        }
    }
}
// @lc code=end

