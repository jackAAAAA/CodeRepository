/*
 * @lc app=leetcode.cn id=239 lang=java
 *
 * [239] 滑动窗口最大值
 */

// @lc code=

// We scan the array from 0 to n-1, keep “promising” elements in the deque. The algorithm is amortized O(n) as each element is put and polled once.

// At each i, we keep “promising” elements, which are potentially max number in window [i-(k-1),i] or any subsequent window. This means

// If an element in the deque and it is out of i-(k-1), we discard them. We just need to poll from the head, as we are using a deque and elements are ordered as the sequence in the array

// Now only those elements within [i-(k-1),i] are in the deque. We then discard elements smaller than a[i] from the tail. This is because if a[x] <a[i] and x<i, then a[x] has no chance to be the “max” in [i-(k-1),i], or any other subsequent window: a[i] would always be a better candidate.

// As a result elements in the deque are ordered in both sequence in array and their value. At each step the head of the deque is the max element in [i-(k-1),i]

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int len = nums.length, ri = 0;
        int[] res = new int[len - k + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < len; ++i) {
            if (!queue.isEmpty() && queue.peekFirst() < i - k + 1) {
                queue.pollFirst();
            }
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }
            queue.offerLast(i);
            if (i >= k - 1) {
                res[ri++] = nums[queue.peekFirst()];
            }
        }
        return res;
    }
}
// @lc code=end

