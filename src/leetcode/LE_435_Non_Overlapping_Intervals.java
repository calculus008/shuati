package leetcode;

import java.util.Arrays;

public class LE_435_Non_Overlapping_Intervals {
    /**
     * Given an array of intervals where intervals[i] = [starti, endi], return the minimum number of intervals
     * you need to remove to make the rest of the intervals non-overlapping.
     *
     * Example 1:
     * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
     * Output: 1
     * Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
     *
     * Example 2:
     * Input: intervals = [[1,2],[1,2],[1,2]]
     * Output: 2
     * Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.
     *
     * Example 3:
     * Input: intervals = [[1,2],[2,3]]
     * Output: 0
     * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
     *
     * Constraints:
     * 1 <= intervals.length <= 2 * 104
     * intervals[i].length == 2 (!!!)
     * -2 * 104 <= starti < endi <= 2 * 104
     *
     * Medium
     */

    /**
     * Interval
     * Almost the exact problem as LE_452_Minimum_Number_Of_Arrows_To_Burst_Balloons.
     *
     * The conditions "intervals[i].length == 2" is set so that each interval is 2 unit long and hence you only need to
     * consider that the case that each interval can only have 1 overlapped interval.(!!!)
     *   |__|
     * |__||__|
     *
     * Therefore, we use the exact same logic as LE_452_Minimum_Number_Of_Arrows_To_Burst_Balloons to count how many
     * intervals  does not have overlap, then the answer is the total number of intervals minus the none-overlap count.
     */
    class Solution {
        public int eraseOverlapIntervals(int[][] intervals) {
            if (intervals == null || intervals.length == 0) return 0;

            Arrays.sort(intervals, (a, b) -> (Integer.compare(a[0], b[0])));
            int count = 1;
            int minEnd = Integer.MAX_VALUE;

            for (int[] p : intervals) {
                if (p[0] >= minEnd) {//The interval here is half close half open, [1, 2).
                    minEnd = p[1];
                    count++;
                } else {
                    minEnd = Math.min(p[1], minEnd);
                }
            }

            return intervals.length - count;//!!!
        }
    }
}
