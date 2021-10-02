package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class LE_862_Shortest_Subarray_With_Sum_At_Least_K {
    /**
     * Return the length of the shortest, non-empty, contiguous subarray of A with sum at least K.
     *
     * If there is no non-empty subarray with sum at least K, return -1.
     *
     * Example 1:
     * Input: A = [1], K = 1
     * Output: 1
     *
     * Example 2:
     * Input: A = [1,2], K = 4
     * Output: -1
     *
     * Example 3:
     * Input: A = [2,-1,2], K = 3
     * Output: 3
     *
     * Note:
     *
     * 1 <= A.length <= 50000
     * -10 ^ 5 <= A[i] <= 10 ^ 5
     * 1 <= K <= 10 ^ 9
     *
     * Hard
     */

    /**
     * Mono Stack Solution
     * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/143726/C%2B%2BJavaPython-O(N)-Using-Deque
     *
     * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/discuss/189039/Detailed-intuition-behind-Deque-solution
     *
     * What makes this problem hard is that we have negative values. Because of negative number,
     * we can use the solution in LE_209_Minimum_Size_Subarray_Sum.
     *
     * Calculate prefix sum "presum" of list A.
     * presum[j] - presum[i] represents the sum of subarray A[i] ~ A[j-1]
     * Deque d will keep indexes of increasing presum[i].
     * For every presum[i], we will compare presum[i] - presum[d[0]] with K.
     *
     * Complexity:
     * Every index will be pushed exactly once.
     * Every index will be popped at most once.
     *
     * Why keep the deque increase?
     * If preSum[dq.getLast()] > preSum[i] and moreover we already know that i > dq.getLast(), it means that compared with dq.getLast(),
     * B[i] can help us make the subarray length SHORTER and sum BIGGER. So no need to keep d.back() in our deque.
     *
     * Time O(N)
     * Space O(N)
     *
     * Use Mono Stack to get max length:
     * LE_962_Maximum_Width_Ramp
     */
    class Solution {
        public int shortestSubarray(int[] A, int K) {
            Deque<Integer> dq = new ArrayDeque<>();
            int[] preSum = new int[A.length + 1];

            for (int i = 0; i < A.length; i++) {
                preSum[i + 1] = preSum[i] + A[i];
            }

            int res = Integer.MAX_VALUE;

            for (int i = 0; i < preSum.length; i++) {
                while (!dq.isEmpty() && preSum[i] - preSum[dq.getFirst()] >= K) {
                    res = Math.min(res, i - dq.pollFirst());
                }

                while (!dq.isEmpty() && preSum[dq.getLast()] > preSum[i]) {
                    dq.pollLast();
                }

                dq.offer(i);
            }

            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }
}
