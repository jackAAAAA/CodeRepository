/*
 * @lc app=leetcode.cn id=773 lang=java
 *
 * [773] 滑动谜题
 */

// @lc code=start
class Solution {
    public int slidingPuzzle(int[][] board) {
        char[] ca = new char[6];
        int index = 0;
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                ca[index++] = (char) (board[i][j] + '0');
            }
        }
        String begin = String.valueOf(ca);
        Queue<String> queue = new LinkedList<>();
        queue.add(begin);
        Set<String> visited = new HashSet<>();
        String target = "123450";
        int min = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String cur = queue.poll();
                if (cur.equals(target)) {
                    return min;
                }
                int pos = cur.indexOf('0');
                int[] exchange = exchangeArray[pos];
                for (int next : exchange) {
                    String nw = exchangeString(cur, pos, next);
                    if (!visited.contains(nw)) {
                        visited.add(nw);
                        queue.add(nw);
                    }
                }
            }
            ++min;
        }
        return -1;
    }
    private int[][] exchangeArray = {
        {1, 3}, {0, 2, 4}, {1, 5},
        {0, 4}, {1, 3, 5}, {2, 4}
    };
    public String exchangeString(String cur, int src, int des) {
        char[] ca = cur.toCharArray();
        char temp = ca[src];
        ca[src] = ca[des];
        ca[des] = temp;
        return String.valueOf(ca);
    } 
}
// @lc code=end

