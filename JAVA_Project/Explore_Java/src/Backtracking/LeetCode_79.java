package Backtracking;

public class LeetCode_79 {
    boolean isValid = false;
    public boolean exist(char[][] board, String word) {
        int row = board.length, col = board[0].length;
        Trie root = buildTrie(word);
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (!isValid) {
                    backtrack(board, row, col, i, j, root);
                } else {
                    return true;
                }
            }
        }
        return isValid;
    }

    void backtrack(char[][] board, int row, int col, int sx, int sy, Trie root) {
        int c = board[sx][sy];
        if (board[sx][sy] == '#' || root.next[c] == null) {
            return;
        }
        board[sx][sy] = '#';
        root = root.next[c];
        if (root.word != null) {
            isValid = true;
            return;
        }
        if (sy < col - 1) {
            backtrack(board, row, col, sx, sy + 1, root);
        }
        if (sx < row - 1) {
            backtrack(board, row, col, sx + 1, sy, root);
        }
        if (sy > 0) {
            backtrack(board, row, col, sx, sy - 1, root);
        }
        if (sx > 0) {
            backtrack(board, row, col, sx - 1, sy, root);
        }
        board[sx][sy] = (char) c;
    }

    Trie buildTrie(String word) {
        Trie root = new Trie();
        Trie p = root;
        for (char i : word.toCharArray()) {
            if (p.next[i] == null) {
                p.next[i] = new Trie();
            }
            p = p.next[i];
        }
        p.word = word;
        return root;
    }
    class Trie {
        Trie[] next;
        String word;

        public Trie() {
            next = new Trie[128];
        }
    }
}

