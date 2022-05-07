/*
 * @lc app=leetcode.cn id=49 lang=java
 *
 * [49] 字母异位词分组
 */

// @lc code=start
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> hashmap = new HashMap<>();
        for (String str : strs) {
            char[] alphabet = new char[26];
            for (char c : str.toCharArray()) {
                ++alphabet[c - 'a'];
            }
            String key = String.valueOf(alphabet);
            hashmap.putIfAbsent(key, new ArrayList<>());
            hashmap.get(key).add(str);
        }
        res.addAll(hashmap.values());
        return res;
    }
}
// @lc code=end

