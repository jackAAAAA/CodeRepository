/*
 * @lc app=leetcode.cn id=126 lang=java
 *
 * [126] 单词接龙 II
 */

// @lc code=start
class Solution {
    // TO DO
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<List<String>>();
        Set<String> dict = new HashSet<String>(wordList);
        if (!dict.contains(endWord)) {
            return res;
        }
        dict.remove(endWord);
        Map<String, Integer> steps = new HashMap<String, Integer>();
        steps.put(beginWord, 0);
        Map<String, List<String>> from = new HashMap<String, List<String>>();
        int step = 1;
        boolean found = false;
        int wordLen = beginWord.length();
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        // Conquer down ↓
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                String curWord = queue.poll();
                char[] charArray = curWord.toCharArray();
                for (int j = 0; j < charArray.length; ++j) {
                    char origin = charArray[j];
                    for (char c = 'a'; c <= 'z'; ++c) {
                        charArray[j] = c;
                        String nextWord = String.valueOf(charArray);
                        if (nextWord.equals(endWord)) {
                            from.putIfAbsent(nextWord, new ArrayList<String>());
                            from.get(nextWord).add(curWord);
                            found = true;
                        }
                        if (steps.containsKey(nextWord) && step == steps.get(nextWord)) {
                            from.get(nextWord).add(curWord);
                        }
                        if (!dict.contains(nextWord)) {
                            continue;
                        }
                        dict.remove(nextWord);
                        from.putIfAbsent(nextWord, new ArrayList<String>());
                        from.get(nextWord).add(curWord);
                        steps.put(nextWord, step);
                        queue.offer(nextWord);
                    }
                    charArray[j] = origin;
                }
            }
            ++step;
            if (found) {
                break;
            }
        }
        if (found) {
            Deque<String> path = new ArrayDeque<String>();
            path.addLast(endWord);
            backtrack(from, path, beginWord, endWord, res);
        }
        return res;
    }

    private void backtrack(Map<String, List<String>> from, Deque<String> path, String beginWord, String cur, List<List<String>> res) {
        if (cur.equals(beginWord)) {
            res.add(new ArrayList<String>(path));
            return;
        }
        for (String precursor : from.get(cur)) {
            path.offerFirst(precursor);
            backtrack(from, path, beginWord, precursor, res);
            path.pollFirst();
        }
    }

}
// @lc code=end
