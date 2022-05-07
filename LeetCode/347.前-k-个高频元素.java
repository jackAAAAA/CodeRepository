/*
 * @lc app=leetcode.cn id=347 lang=java
 *
 * [347] 前 K 个高频元素
 */

// @lc code=start
class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> hashmap = new HashMap<Integer, Integer>();
        for (int num : nums) {
            hashmap.put(num, hashmap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> smallheap = new PriorityQueue<Integer>((Integer k1, Integer k2) -> hashmap.get(k1) - hashmap.get(k2));

        // for (int key : hashmap.keySet()) {
        //     if (smallheap.size() < k) {
        //         smallheap.offer(key);
        //     } else if (hashmap.get(key) > hashmap.get(smallheap.peek())) {
        //         smallheap.poll();
        //         smallheap.offer(key);
        //     }
        // }
        for (Map.Entry<Integer, Integer> entry : hashmap.entrySet()) {
            if (smallheap.size() < k) {
                smallheap.offer(entry.getKey());
            } else if (entry.getValue() > hashmap.get(smallheap.peek())) {
                smallheap.poll();
                smallheap.offer(entry.getKey());
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; ++i) {
            res[i] = smallheap.poll();
        }

        return res;

    }
}
// @lc code=end

