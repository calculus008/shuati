package leetcode;

import java.util.*;

public class LE_857_Minimum_Cost_To_Hire_K_Workers {
    /**
     * There are n workers. You are given two integer arrays quality and wage where quality[i] is the quality of the ith
     * worker and wage[i] is the minimum wage expectation for the ith worker.
     *
     * We want to hire exactly k workers to form a paid group. To hire a group of k workers, we must pay them according
     * to the following rules:
     *
     * Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid
     * group. Every worker in the paid group must be paid at least their minimum wage expectation.
     * Given the integer k, return the least amount of money needed to form a paid group satisfying the above conditions.
     * Answers within 10-5 of the actual answer will be accepted.
     *
     * Example 1:
     * Input: quality = [10,20,5], wage = [70,50,30], k = 2
     * Output: 105.00000
     * Explanation: We pay 70 to 0th worker and 35 to 2nd worker.
     *
     * Example 2:
     * Input: quality = [3,1,10,10,1], wage = [4,8,2,2,7], k = 3
     * Output: 30.66667
     * Explanation: We pay 4 to 0th worker, 13.33333 to 2nd and 3rd workers separately.
     *
     * Constraints:
     * n == quality.length == wage.length
     * 1 <= k <= n <= 104
     * 1 <= quality[i], wage[i] <= 104
     *
     * Hard
     *
     * https://leetcode.com/problems/minimum-cost-to-hire-k-workers/
     */

    /**
     * sorting + PriorityQueue
     *
     * Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid group.
     *
     *   cost[i]        quality[i]           cost[i]        cost[j]
     *  _________  =    ________       --->   _________  =    ________
     *   cost[j]        quality[j]          quality[i]     quality[j]
     *
     *
     *   cost[i]        quality[i]
     *  _________  =    ________
     *  total cost      total quality
     *
     * So
     *
     * cost[i]  / quality[i] =  cost[j]  / quality[j]
     *
     * The group of workers share the same "cost[i]  / quality[i]", let's call it PAID_RATIO
     * cost[i]  / quality[i] = total cost / total quality
     *
     * PAID_RATIO = total cost / total quality
     * Every worker in the paid group must be paid at least their minimum wage expectation.
     *
     * cost[i] >= wage[i]
     * So:
     * cost[i] / quality[i] >= wage[i] / quality[i]
     *
     * PAID_RATIO must be larger than or equal to maximum wage[i] / quality[i]  (let's call it RATIO) within the group.
     *
     * According to PAID_RATIO = total cost / total quality ---> total cost = PAID_RATIO * total quality
     * To minimize the total cost, we want a small ratio.
     * So we sort all workers with their PAID_RATIO (small to large), and pick up K first workers.
     * Now we have a minimum possible ratio for K worker and their total quality.
     *
     * As we pick up next worker with bigger ratio, we increase the ratio for whole group.
     * Meanwhile we remove a worker when group size is more than k so that we keep K workers in the group.
     *
     * Which worker should we remove? According to PAID_RATIO = total cost / total quality ---> total cost = PAID_RATIO * total quality
     * the less total quality, the better (the less of total cost). So we remove the worker with maximum quality in group when group is full.
     *
     * We calculate the current total cost = PAID_RATIO * total quality for this group.
     *
     * We redo the process and we can find the minimum total wage.
     * Because workers are sorted by ratio of wage/quality.
     * For every ratio, we find the minimum possible total quality of K workers.
     *
     * Time  : O(nlogn + nlogk)
     * Space : O(n)
     */
    class Solution {
        public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
            /**
             * Nice trick to operate only on worker index (worker ID), all info can be derived from worker index.
             * No need to create a separate class or use double array.
             * O(n)
             */
            List<Integer> workers = new ArrayList<>();
            for (int i = 0; i < wage.length; i++) {
                workers.add(i);
            }

            /**
             * sort based on PAID_RATIO
             * O(nlogn)
             */
            Collections.sort(workers, (a, b) -> Double.compare(wage[a] / (double)quality[a], wage[b] / (double)quality[b]));

            /**
             * Max Heap based on quality.
             * In the end, the heap contains all the indices (IDs) of picked workers.
             */
            PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(quality[b], quality[a]));

            double res = Double.MAX_VALUE;

            int qualitySum = 0;

            /**
             * O(nlogk)
             */
            for (int w : workers) {
                pq.offer(w);
                qualitySum += quality[w];

                if (pq.size() > k) {
                    qualitySum -= quality[pq.poll()];
                }

                if (pq.size() == k) {
                    res = Math.min(res, qualitySum * (wage[w] / (double)quality[w]));
                }
            }

            return res;
        }
    }
}
