/*
 * @lc app=leetcode.cn id=208 lang=java
 *
 * [208] 实现 Trie (前缀树)
 */

// @lc code=start
class Trie {
    private Trie[] next;
    private boolean isEnd;
    public Trie() {
        next = new Trie[26];
        isEnd = false;
    }
    public void insert(String word) {
        Trie cur = this;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (cur.next[i] == null) {
                cur.next[i] = new Trie();
            }
            cur = cur.next[i];
        }
        cur.isEnd = true;
    }
    public boolean search(String word) {
        Trie cur = searchPrefix(word);
        return cur != null && cur.isEnd;
    }
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) == null ? false : true;
    }
    public Trie searchPrefix(String word) {
        Trie cur = this;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (cur.next[i] == null) {
                return null;
            }
            cur = cur.next[i];
        }
        return cur;
    }
}
/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
// @lc code=end

