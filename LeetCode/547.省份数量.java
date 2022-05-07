/*
 * @lc app=leetcode.cn id=547 lang=java
 *
 * [547] 省份数量
 */

// @lc code=start
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int num = isConnected.length;
        DisjointSet un = new DisjointSet(num);
        for (int i = 0; i < num - 1; ++i) {
            for (int j = i + 1; j < num; ++j) {
                if (isConnected[i][j] == 1) {
                    un.union(i, j);
                }
            }
        }
        return un.count();
    }
    class DisjointSet {
        private int[] parent;
        private int count;
        public DisjointSet(int n) {
            parent = new int[n];
            count = n;
            for (int i = 0; i < n; ++i) {
                parent[i] = i;
            }
        }
        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) {
                return;
            }
            parent[rootP] = rootQ;
            --count;
        }
        public int count() {
            return count;
        }
    }
}
// @lc code=end

