package leetcode;

public class LE_1326_Minimum_Number_Of_Taps_To_Open_To_Water_A_Garden {
    /**
     * There is a one-dimensional garden on the x-axis. The garden starts at the point 0 and ends at the point n.
     * (i.e The length of the garden is n).
     *
     * There are n + 1 taps located at points [0, 1, ..., n] in the garden.
     *
     * Given an integer n and an integer array ranges of length n + 1 where ranges[i] (0-indexed) means the i-th tap
     * can water the area [i - ranges[i], i + ranges[i]] if it was open.
     *
     * Return the minimum number of taps that should be open to water the whole garden, If the garden cannot be watered return -1.
     *
     * Example 1:
     * Input: n = 5, ranges = [3,4,1,1,0,0]
     * Output: 1
     * Explanation: The tap at point 0 can cover the interval [-3,3]
     * The tap at point 1 can cover the interval [-3,5]
     * The tap at point 2 can cover the interval [1,3]
     * The tap at point 3 can cover the interval [2,4]
     * The tap at point 4 can cover the interval [4,4]
     * The tap at point 5 can cover the interval [5,5]
     * Opening Only the second tap will water the whole garden [0,5]
     *
     * Example 2:
     * Input: n = 3, ranges = [0,0,0,0]
     * Output: -1
     * Explanation: Even if you activate all the four taps you cannot water the whole garden.
     *
     * Example 3:
     * Input: n = 7, ranges = [1,2,1,0,2,1,0,1]
     * Output: 3
     *
     * Example 4:
     * Input: n = 8, ranges = [4,0,0,0,0,0,0,0,4]
     * Output: 2
     *
     * Example 5:
     * Input: n = 8, ranges = [4,0,0,0,4,0,0,0,4]
     * Output: 1
     *
     * Constraints:
     * 1 <= n <= 10^4
     * ranges.length == n + 1
     * 0 <= ranges[i] <= 100
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/discuss/506853/Java-A-general-greedy-solution-to-process-similar-problems
     *
     * Greedy
     * First convert to LE_45_Jump_Game_II, then use the exact solution.
     *
     * Same type problems:
     * LE_45_Jump_Game_II
     * LE_1024_Video_Stitching
     */
    class Solution {
        public int minTaps(int n, int[] ranges) {
            /**
             * Create an array that make the problem same as LE_45_Jump_Game_II
             *
             * Similar to LE_45_Jump_Game_II, we also deal with a range - [start, end]
             * min value of start should be 0.
             *
             * NOTE: the only difference from LE_45_Jump_Game_II is:
             * arr[], index is the start index of a given range, arr[index] is the max end value of all ranges that have
             * start value at index.
             *
             * Example:
             * ranges = [3,4,1,1,0,0], for each index, its range is:
             * [-3,5], [1,3], [2,4], [4,4], [5,5], if we cap left at 0, it becomes:
             * [0,5], [1,3], [2,4], [4,4], [5,5]
             * Conver it arr:
             *
             * [5, 3, 4, 0, 4, 5]
             *
             */
            int[] arr = new int[n + 1];
            for (int i = 0; i < ranges.length; i++) {
                if (ranges[i] == 0) continue;
                int left = Math.max(0, i - ranges[i]);
                arr[left] = Math.max(arr[left], i + ranges[i]);
            }

            int curEnd = 0, curfarthest = 0, res = 0;

            for (int i = 0; curEnd < arr.length - 1; curEnd = curfarthest) {
                res++;

                while (i < arr.length && i <= curEnd) {
                    /**
                     * !!!
                     * Difference from LE_45_Jump_Game_II:
                     * "Math.max(curfarthest, arr[i])", not "Math.max(curfarthest, i + arr[i])"
                     * since arr[i] is already the right side boundary.
                     */
                    curfarthest = Math.max(curfarthest, arr[i]);
                    i++;
                }

                /**
                 * we can't go any further from this point.
                 */
                if (curEnd == curfarthest) return -1;
            }

            return res;
        }
    }
}
