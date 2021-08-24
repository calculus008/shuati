package leetcode;

import java.util.*;

public class LE_1552_Magnetic_Force_Between_Two_Balls {
    /**
     * In universe Earth C-137, Rick discovered a special form of magnetic force between two balls if they are put in
     * his new invented basket. Rick has n empty baskets, the ith basket is at position[i], Morty has m balls and needs
     * to distribute the balls into the baskets such that the minimum magnetic force between any two balls is maximum.
     *
     * Rick stated that magnetic force between two different balls at positions x and y is |x - y|.
     *
     * Given the integer array position and the integer m. Return the required force.
     *
     * Example 1:
     * Input: position = [1,2,3,4,7], m = 3
     * Output: 3
     * Explanation: Distributing the 3 balls into baskets 1, 4 and 7 will make the magnetic force between ball pairs
     * [3, 3, 6]. The minimum magnetic force is 3. We cannot achieve a larger minimum magnetic force than 3.
     *
     *  Example 2:
     * Input: position = [5,4,3,2,1,1000000000], m = 2
     * Output: 999999999
     * Explanation: We can use baskets 1 and 1000000000.
     *
     * Constraints:
     * n == position.length
     * 2 <= n <= 10^5
     * 1 <= position[i] <= 10^9
     * All integers in position are distinct.
     * 2 <= m <= position.length
     *
     * Hard
     */

    /**
     * Binary Search
     *
     * Let x be the required answer. Then, we know that we can also place all m balls even if minimum distance between
     * any two were 1, 2, ... (x-1). But, we cannot place all of them if minimum distance between any two were (x+1), (x+2), ...
     *
     * Search space becomes:
     * 1, 2, 3, 4, 5, ... x, x+1, x+2, x+3 (Each number represents minimum distance between each ball)
     * T, T, T, T, T, ..... T, F, F, F .... (Can we place all m balls if minimum distance were above number? T = true, F = false)
     *
     * Now, we need to find last occurrence of true in above array.
     */
    class Solution {
        public int maxDistance(int[] position, int m) {
            /**
             * !!!
             * From example 2, we know that given position[] is not sorted, must sort first.
             */
            Arrays.sort(position);

            /**
             * We do binary search on distance, the range is 1 to position[position.length - 1]
             * NOT position.length - 1 !!!
             */
            int l = 1;
            int r = position[position.length - 1];

            int optimal = 0;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (canPut(position, m, mid)) {
                    /**
                     * record the last True case as return value.
                     */
                    optimal = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return optimal;

            /**
             *             //This binary search logic also works
             *             while (l < r) {
             *                 int mid = l + (r - l) / 2;
             *                 if (canPut(position, m, mid)) {
             *                     l = mid + 1;
             *                 } else {
             *                     r = mid;
             *                 }
             *             }
             *             return l - 1;
             */
        }

        /**
         * Given min distance value "x", check if we can place all m balls
         */
        private boolean canPut(int[] position, int m, int minDist) {
            int count = 1;
            int lastPos = position[0];

            for (int i = 1; i < position.length; i++) {
                int curPos = position[i];
                if (curPos - lastPos >= minDist) {
                    lastPos = curPos;
                    count++;
                }
            }
            return count >= m;
        }
    }
}
