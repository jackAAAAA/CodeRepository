/*
 * @lc app=leetcode.cn id=212 lang=java
 *
 * [212] 单词搜索 II
 */

// @lc code=start
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        int row = board.length, col = board[0].length;
        Trie root = buildTrie(words);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                backtrack(board, i, j, root, res);
            }
        }
        return res;
    }
    public void backtrack(char[][] board, int i, int j, Trie root, List<String> res) {
        int c = board[i][j] - 'a';
        // TO DO
        if (board[i][j] == '#' || root.next[c] == null) {
            return;
        }
        root = root.next[c];
        if (root.word != null) {
            res.add(root.word);
            root.word = null;
        }
        board[i][j] = '#';
        if (j < board[0].length - 1) {
            backtrack(board, i, j + 1, root, res);
        }
        if (i < board.length - 1) {
            backtrack(board, i + 1, j, root, res);
        }
        if (j > 0) {
            backtrack(board, i, j - 1, root, res);
        }
        if (i > 0) {
            backtrack(board, i - 1, j, root, res);
        }
        board[i][j] = (char) (c + 'a');
    }
    public Trie buildTrie(String[] words) {
        Trie root = new Trie();
        for (String w : words) {
            Trie p = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) {
                    p.next[i] = new Trie();
                }
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }
    class Trie {
        private Trie[] next = new Trie[26];
        private String word;
    }
}
// @lc code=end

