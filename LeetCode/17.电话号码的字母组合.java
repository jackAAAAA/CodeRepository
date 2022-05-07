/*
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 */

// @lc code=start
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        char[] ca = digits.toCharArray();
        int len = ca.length;
        if (len == 0) {
            return list;
        }
        String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrack(ca, len, map, new StringBuilder(), 0, list);
        return list;
    }
    private void backtrack(char[] ca, int len, String[] map, StringBuilder sb, int start, List<String> list) {
        if (start == len) {
            list.add(sb.toString());
            return;
        }
        int index = ca[start] - '0';
        for (int i = 0; i < map[index].length(); ++i) {
            sb.append(map[index].charAt(i));
            backtrack(ca, len, map, sb, start + 1, list);
            sb.setLength(sb.length() - 1);
        }
    }
}
// @lc code=end

