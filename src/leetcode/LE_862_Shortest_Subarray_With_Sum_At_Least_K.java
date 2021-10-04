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
     * Mono Stack Solution (Mono Deque, need to operate on both ends of the queue)
     *
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
     * # How to think of such solutions?
     * Basic idea, for array starting at every A[i], find the shortest one with sum at least K.
     * In my solution, for preSum[i], find the smallest j that preSum[j] - preSum[i] >= K.
     * Keep this in mind for understanding two while loops.
     *
     * # What is the purpose of first while loop?
     * For the current prefix sum preSum[i], it covers all subarray ending at A[i-1].
     * We want know if there is a subarray, which starts from an index, ends at A[i-1] and has at least sum K.
     * So we start to compare preSum[i] with the smallest prefix sum in our deque, which is  preSum[dq.getFirst()],
     * hoping that preSum[i] -  preSum[dq.getFirst()] >= K. So if preSum[i] -  preSum[dq.getFirst()] >= k, we can update
     * our result res = min(res, i - dq.pollFirst()).
     * The while loop helps comparing one by one, until this condition isn't valid anymore.
     *
     * # What is the purpose of second while loop?
     * To keep preSum[i] increasing in the deque.
     *
     * # Why keep the deque increase?
     * If preSum[dq.getLast()] > preSum[i] and moreover we already know that i > dq.getLast(), it means that compared with dq.getLast(),
     * preSum[i] can help us make the subarray length SHORTER and sum BIGGER. So no need to keep d.back() in our deque.
     *
     * Time O(N)
     * Space O(N)
     *
     * Use Mono Stack to get max length:
     * LE_962_Maximum_Width_Ramp
     */
    class Solution {
        public int shortestSubarray(int[] bulbs, int K) {
            Deque<Integer> dq = new ArrayDeque<>();
            int[] preSum = new int[bulbs.length + 1];

            for (int i = 0; i < bulbs.length; i++) {
                preSum[i + 1] = preSum[i] + bulbs[i];
            }

            int res = Integer.MAX_VALUE;

            for (int i = 0; i < preSum.length; i++) {
                /**
                 * 1.calculate len when sum >= k condition holds
                 *  "dq.getFirst()", the later the index is added to dq, the larger the index, hence the shorter the sum.
                 *
                 *  Queue First : has the earliest element that enters the queue, getFirst() - consuming side
                 */
                while (!dq.isEmpty() && preSum[i] - preSum[dq.getFirst()] >= K) {
                    res = Math.min(res, i - dq.pollFirst());
                }

                /**
                 * 2.keep deque increasing
                 *
                 * Queue Last : has the latest element that enters the queue, getLast() - producing side
                 */
                while (!dq.isEmpty() && preSum[dq.getLast()] > preSum[i]) {
                    dq.pollLast();
                }
                dq.offer(i);
            }

            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }
}
