package leetcode;

import java.util.PriorityQueue;

public class LE_1167_Minimum_Cost_To_Connect_Sticks {
    /**
     * You have some sticks with positive integer lengths.
     *
     * You can connect any two sticks of lengths X and Y into one stick by paying a cost of X + Y.
     * You perform this action until there is one stick remaining.
     *
     * Return the minimum cost of connecting all the given sticks into one stick in this way.
     *
     * Example 1:
     * Input: sticks = [2,4,3]
     * Output: 14
     * Step 1:
     * cost -> 2+3 = 5
     * result -> [5,4]
     * Step 2:
     * cost -> 5+4 = 9
     * result -> [9]
     * FINAL COST =5 + 9 = 14
     *
     *
     * Example 2:
     * Input: sticks = [1,8,3,5]
     * Output: 30
     *step 1:
     * cost -> 1+3 = 4
     * result -> [4,8,5]
     * step 2:
     * cost -> 4+5 = 9
     * result -> [9,8]
     * step 3:
     * cost -> 9+8 = 17
     * result = [17]
     * FINAL COST = 4 + 9 + 17 = 30
     *
     * Constraints:
     * 1 <= sticks.length <= 10^4
     * 1 <= sticks[i] <= 10^4
     *
     * Medium
     */

    /**
     * PQ
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution1 {
        public int connectSticks(int[] sticks) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();

            for (int stick : sticks) {
                pq.offer(stick);
            }

            int res= 0;

            while (pq.size() >= 2) {
                int n = pq.poll() + pq.poll();
                res += n;
                pq.offer(n);
            }

            return res;
        }
    }
}
