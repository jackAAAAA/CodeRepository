import java.util.Deque;

/*
 * @lc app=leetcode.cn id=20 lang=java
 *
 * [20] 有效的括号
 */

// @lc code=start
class Solution {
    public boolean isValid(String s) {
        char[] ca = s.toCharArray();
        Map<Character, Character> map = new HashMap<>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : ca) {
            if (map.containsKey(c)) {
                if (stack.isEmpty() || map.get(c) != stack.pollLast()) {
                    return false;
                } 
            } else {
                stack.offerLast(c);
            }
        }
        return stack.isEmpty();
    }
}
// @lc code=end

