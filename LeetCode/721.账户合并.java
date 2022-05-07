/*
 * @lc app=leetcode.cn id=721 lang=java
 *
 * [721] 账户合并
 */

// @lc code=start
class Solution {

    /**
     * 一个账户可能对应多个邮箱，但是一个邮箱只能对应一个账户
     * 基于以上事实，就可以利用两个map来解决这个问题
     * mailToindex:one-to-one mapping-mail String to its parent index mapping
     * insertMail:one-to-many mapping-parent index to all emails that belong to the same group mapping
     * @param accounts
     * @return result
     * 时间复杂度：O(n*logn)，m:账号的数量；n:不同邮箱地址的数量。n个不同的邮箱都会遍历一次，需要O(n)；同时在并查集中查找2n次，最多合并n次，一次操作需要O(logn)
     * 空间复杂度：O(n)，n:哈希表存储不同邮箱的数量以及并查集存储不同邮箱地址父结点的数量。
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        
        int num_account = accounts.size();
        DisjointSet dj = new DisjointSet(num_account);
        
        // Step 1: traverse all emails except names, if we have not seen an email before, put it with its index into map.
        // Otherwise, union the email to its parent index.
        Map<String, Integer> mailToIndex = new HashMap<String, Integer>();
        for (int i = 0; i < num_account; ++i) {
            for (int j = 1; j < accounts.get(i).size(); ++j) {
                String curMail = accounts.get(i).get(j);
                if (mailToIndex.containsKey(curMail)) {
                    int p1Index = mailToIndex.get(curMail);
                    dj.union(p1Index, i);
                } else {
                    mailToIndex.put(curMail, i);
                }
            }
        }
        
        // Step 2: traverse every email list, find the parent of current list index and put all emails into the set list
        // that belongs to key of its parent index
        Map<Integer, Set<String>> insertMail = new HashMap<Integer, Set<String>>();
        for (int i = 0; i < num_account;++i) {
            int p2Index = dj.find(i);
            insertMail.putIfAbsent(p2Index, new TreeSet<String>());
            
            Set<String> curSet = insertMail.get(p2Index);
            for (int j = 1; j < accounts.get(i).size(); ++j) {
                curSet.add(accounts.get(i).get(j));
            }
            insertMail.put(p2Index, curSet);
        }
        
        // step 3: traverse keySet of disjoint set group, retrieve all emails from each parent index, and then SORT
        // them, as well as adding the name at index 0 of every sublist
        List<List<String>> result = new ArrayList<List<String>>();
        for (int index : insertMail.keySet()) {
            List<String> curList = new ArrayList<String>();
            curList.addAll(insertMail.get(index));
            curList.add(0, accounts.get(index).get(0));
            result.add(curList);
        }

        return result;

    }

    class DisjointSet {
        private int count;
        private int[] parent;
        DisjointSet (int num) {
            count = num;
            parent = new int[num];
            for (int i = 0; i < num; ++i) {
                parent[i] = i;
            }
        }
        public int find (int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }
        public void union (int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) {
                return;
            }
            parent[rootQ] = rootP;
            --count;
        }
    }
}
// @lc code=end

