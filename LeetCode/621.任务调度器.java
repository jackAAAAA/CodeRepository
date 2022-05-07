/*
 * @lc app=leetcode.cn id=621 lang=java
 *
 * [621] 任务调度器
 */

// @lc code=start
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int len = tasks.length;
        int max = 0, counter = 1;
        int[] cnt = new int[26];
        for (char t : tasks) {
            ++cnt[t - 'A'];
        }
        for (int mem : cnt) {
            if (mem == 0) {
                continue;
            } else if (mem > max) {
                max = mem; counter = 1;
            } else if (mem == max) {
                ++counter;
            }
        }
        int res = (max - 1) * (n + 1) + counter;
        return res < len ? len : res;
    }
}
// @lc code=end

