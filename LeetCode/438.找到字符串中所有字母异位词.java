/*
 * @lc app=leetcode.cn id=438 lang=java
 *
 * [438] 找到字符串中所有字母异位词
 */

// @lc code=start
class Solution {
    public List<Integer> findAnagrams(String s, String t) {
        char[] ca_s = s.toCharArray(), ca_t = t.toCharArray();
        int sl = ca_s.length, tl = ca_t.length;
        int[] needs = new int[26], window = new int[26];
        for (char c : ca_t) {
            ++needs[c - 'a'];
        }
        int left = 0, right = 0;
        List<Integer> res = new ArrayList<>();
        while (right < sl) {
            int curR = ca_s[right++] - 'a';
            ++window[curR];
            while (window[curR] > needs[curR]) {
                int curL = ca_s[left++] - 'a';
                --window[curL];
            }
            if (right - left == tl) {
                res.add(left);
            }
        }
        return res;
    }
}
// @lc code=end

