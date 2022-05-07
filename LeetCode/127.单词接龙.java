/*
 * @lc app=leetcode.cn id=127 lang=java
 *
 * [127] 单词接龙
 */

// @lc code=start
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        Set<String> visited = new HashSet<>();
        int step = 1;
        while (!queue.isEmpty()) {
            int curSize = queue.size();
            while (curSize-- > 0) {
                String curWord = queue.poll();
                char[] ca = curWord.toCharArray();
                for (int i = 0; i < ca.length; ++i) {
                    char oc = ca[i];
                    for (char c = 'a'; c <= 'z'; ++c) {
                        if (c == ca[i]) {
                            continue;
                        }
                        ca[i] = c;
                        String nw = String.valueOf(ca);
                        if (wordSet.contains(nw)) {
                            if (nw.equals(endWord)) {
                                return ++step;
                            }
                            if (!visited.contains(nw)) {
                                queue.add(nw);
                                visited.add(nw);
                            }
                        }
                    }
                    ca[i] = oc;
                }
            }
            ++step;
        }
        return 0;
    }
}
// @lc code=end

