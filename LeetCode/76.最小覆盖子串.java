/*
 * @lc app=leetcode.cn id=76 lang=java
 *
 * [76] 最小覆盖子串
 */

// @lc code=start
class Solution {
   
    public String minWindow(String s, String t) {
        char[] ca_s = s.toCharArray(), ca_t = t.toCharArray();
        int sl = ca_s.length, counter = ca_t.length, start = 0, end = 0, minStart = 0, minLen = Integer.MAX_VALUE;
        if (sl < counter) {
            return "";
        }
        int[] map = new int[128];
        for (char c : ca_t) {
            ++map[c];
        }
        while (end < sl) {
            char c1 = ca_s[end];
            if (map[c1] > 0) {
                --counter;
            }
            ++end; --map[c1];
            while (counter == 0) {
                if (minLen > end - start) {
                    minStart = start;
                    minLen = end - start;
                }
                char c2 = ca_s[start];
                ++start; ++map[c2];
                if (map[c2] > 0) {
                    ++counter;
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

}
// @lc code=end

