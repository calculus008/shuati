package leetcode;

import java.util.*;

public class LE_1499_Max_Value_Of_Equation {
    /**
     * You are given an array points containing the coordinates of points on a 2D plane, sorted by the x-values,
     * where points[i] = [xi, yi] such that xi < xj for all 1 <= i < j <= points.length. You are also given an integer k.
     *
     * Return the maximum value of the equation yi + yj + |xi - xj| where |xi - xj| <= k and 1 <= i < j <= points.length.
     *
     * It is guaranteed that there exists at least one pair of points that satisfy the constraint |xi - xj| <= k.
     *
     * Example 1:
     * Input: points = [[1,3],[2,0],[5,10],[6,-10]], k = 1
     * Output: 4
     * Explanation: The first two points satisfy the condition |xi - xj| <= 1 and if we calculate the equation we get
     * 3 + 0 + |1 - 2| = 4. Third and fourth points also satisfy the condition and give a value of 10 + -10 + |5 - 6| = 1.
     * No other pairs satisfy the condition, so we return the max of 4 and 1.
     *
     * Example 2:
     * Input: points = [[0,0],[3,0],[9,2]], k = 3
     * Output: 3
     * Explanation: Only the first two points have an absolute difference of 3 or less in the x-values, and give the value of 0 + 0 + |0 - 3| = 3.
     *
     * Constraints:
     * 2 <= points.length <= 105
     * points[i].length == 2
     * -108 <= xi, yi <= 108
     * 0 <= k <= 2 * 108
     * xi < xj for all 1 <= i < j <= points.length
     * xi form a strictly increasing sequence.
     *
     * Hard
     */

    /**
     * PriorityQueue
     *
     * Because xi < xj, yi + yj + |xi - xj| = (yi - xi) + (yj + xj)
     *
     * xi -> prev[0], yi -> prev[1]
     * xj -> cur[0],  yj -> cur[1]
     *
     * so, prev[1] - prev[0] + cur[1] + cur[0]
     *
     * Imagine we face a new point cur. We try to find the maximum equation value between cur and one of previous
     * point(let's say prev). The formula is cur[0] + cur[1] + (prev[1] - prev[0]). pq aims to find the previous
     * point with greatest (prev[1] - prev[0])
     */
    class Solution1 {
        public int findMaxValueOfEquation(int[][] points, int k) {
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> ((b[1] - b[0]) - (a[1] - a[0])));
            // pq.offer(points[0]);//!!! don't do this with "for (int[] cur : points)", you basically use the first element twice.

            int res = Integer.MIN_VALUE;

            for (int[] cur : points) {
                while (!pq.isEmpty() && cur[0] - pq.peek()[0] > k) {
                    pq.poll();
                }

                if (!pq.isEmpty()) {
                    int[] prev = pq.peek();
                    res = Math.max(res, cur[1] + cur[0] + prev[1] - prev[0]);
                }
                pq.offer(cur);
            }

            return res;
        }
    }

    /**
     * Mono Stack solution
     *
     * Keep the point with max (p[1] - p[0]) at the head of dq (mono decrease stack)
     */
    class Solution2 {
        public int findMaxValueOfEquation(int[][] points, int k) {
            Deque<int[]> dq = new ArrayDeque<>();
            int res = Integer.MIN_VALUE;

            for (int[] cur : points) {
                while (!dq.isEmpty() && cur[0] - dq.peekFirst()[0] > k) {
                    dq.pollFirst();
                }

                if (!dq.isEmpty()) {
                    int[] prev = dq.peekFirst();
                    res = Math.max(res, cur[1] + cur[0] + prev[1] - prev[0]);
                }

                while (!dq.isEmpty() && cur[1] - cur[0] > dq.peekLast()[1] - dq.peekLast()[0]) {
                    dq.pollLast();
                }
                dq.offerLast(cur);
            }

            return res;
        }
    }
}
