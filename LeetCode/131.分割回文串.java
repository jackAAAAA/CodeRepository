/*
 * @lc app=leetcode.cn id=131 lang=java
 *
 * [131] 分割回文串
 */

// @lc code=start
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> list = new ArrayList<List<String>>();
        backtrack(list, new ArrayList<String>(), s, 0);
        return list;
    }
    private void backtrack(List<List<String>> list, List<String> templist, String s, int start) {
        int len = s.length();
        if (start == len) {
            list.add(new ArrayList<String>(templist));
        } else {
            for (int i = start; i < len; ++i) {
                if (isPalindrome(s, start, i)) {
                    templist.add(s.substring(start, i + 1));
                    backtrack(list, templist, s, i + 1);
                    templist.remove(templist.size() - 1);
                }
            }
        }
    }
    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }
}
// @lc code=end

