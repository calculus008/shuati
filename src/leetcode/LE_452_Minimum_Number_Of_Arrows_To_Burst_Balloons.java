package leetcode;

import java.util.*;

public class LE_452_Minimum_Number_Of_Arrows_To_Burst_Balloons {
    /**
     * There are some spherical balloons spread in two-dimensional space. For each balloon, provided input is the start
     * and end coordinates of the horizontal diameter. Since it's horizontal, y-coordinates don't matter, and hence the
     * x-coordinates of start and end of the diameter suffice. The start is always smaller than the end.
     *
     * An arrow can be shot up exactly vertically from different points along the x-axis. A balloon with xstart and xend
     * bursts by an arrow shot at x if xstart ≤ x ≤ xend. There is no limit to the number of arrows that can be shot. An
     * arrow once shot keeps traveling up infinitely.
     *
     * Given an array points where points[i] = [xstart, xend], return the minimum number of arrows that must be shot to
     * burst all balloons.
     *
     * Example 1:
     * Input: points = [[10,16],[2,8],[1,6],[7,12]]
     * Output: 2
     * Explanation: One way is to shoot one arrow for example at x = 6 (bursting the balloons [2,8] and [1,6]) and another
     * arrow at x = 11 (bursting the other two balloons).
     *
     * Example 2:
     * Input: points = [[1,2],[3,4],[5,6],[7,8]]
     * Output: 4
     *
     * Example 3:
     * Input: points = [[1,2],[2,3],[3,4],[4,5]]
     * Output: 2
     *
     * Constraints:
     * 1 <= points.length <= 104
     * points[i].length == 2
     * -231 <= xstart < xend <= 231 - 1
     *
     * Medium
     */

    /**
     * Interval
     * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/discuss/93735/A-Concise-Template-for-%22Overlapping-Interval-Problem%22
     *
     * Sort by starting position
     *
     *   |___| |___|
     *     2     3
     * |_____________|
     *       1
     *
     *  After sorting, 1 and 2 is merged. minEnd is the end position of 2. So 3 won't be able to be merged, so we need
     *  2 arrows.
     *
     *  !!! NOTICE
     *  This type of interval problem is DIFFERENT from LE_56_Merge_Intervals. There, we merge intervals that have overlap
     *  with each other, for the example above:
     *
     *          |___| |___|
     *            2     3
     *       |_____________|
     *             1
     *  It will be merged as one interval, you can think of its projection on the x axle is one interval.
     *
     *  But for this one, for a group of balloons to be shot together, they must have overlap over a common section
     *  or sub-interval range. In the example above, interval 3 has no common overlap section with both 1 and 2.
     *
     *  变形题
     *  LE_435_Non_Overlapping_Intervals
     */
    class Solution2 {
        public int findMinArrowShots(int[][] points) {
            if (points == null || points.length == 0) return 0;

            int count = 1; //!!!
            int minEnd = Integer.MAX_VALUE;
            Arrays.sort(points, (a,b) -> (Integer.compare(a[0], b[0])));   // Sorting the intervals/pairs in ascending order of its starting point

            for (int[] p : points) {
                if (p[0] > minEnd) {//current balloon can't be shot with the same arrow, need shoot a new arrow, hence count++
                    count++;
                    minEnd = p[1];
                } else {
                    // renew key parameters of the active set
                    minEnd = Math.min(minEnd, p[1]);
                }
            }

            return count;
        }
    }

    /**
     * Greedy
     * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/discuss/93703/Share-my-explained-Greedy-solution
     *
     * Idea:
     * We know that eventually we have to shoot down every balloon, so for each balloon there must be an arrow whose
     * position is between balloon[0] and balloon[1] inclusively. Given that, we can sort the array of balloons by their
     * ENDING position (!!!). Then we make sure that while we take care of each balloon in order, we can shoot as many
     * following balloons as possible.
     *
     * So what position should we pick each time? We should shoot as to the right as possible, because since balloons are
     * sorted, this gives you the best chance to take down more balloons. Therefore, the position should always be balloon[i][1]
     * for the ith balloon.
     *
     * This is exactly what I do in the for loop: check how many balloons I can shoot down with one shot aiming at the
     * ending position of the current balloon. Then I skip all these balloons and start again from the next one (or the
     * leftmost remaining one) that needs another arrow.
     *
     * Example:
     * balloons = [[7,10], [1,5], [3,6], [2,4], [1,4]]
     * After sorting, it becomes:
     *
     * balloons = [[2,4], [1,4], [1,5], [3,6], [7,10]]
     * So first of all, we shoot at position 4, we go through the array and see that all first 4 balloons can be taken
     * care of by this single shot. Then we need another shot for one last balloon. So the result should be 2.
     */
    class Solution1 {
        public int findMinArrowShots(int[][] points) {
            if (points.length == 0) return 0;

            /**
             * !!!
             * Why "Integer.compare(a[1], b[1])", not the common "a[1] - b[1]"?
             * Because a[1] - b[1] will overflow for this particular case : [[-2147483646,-2147483645],[2147483646,2147483647]]
             * Int range for Java is -2147483648 to 2147483647
             */
            Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
            int res = 1;
            int cur = points[0][1];

            for (int i = 1; i < points.length; i++) {
                if (cur >= points[i][0]) continue;
                res++;
                cur = points[i][1];
            }

            return res;
        }
    }

    class Solution3 {
        public int findMinArrowShots(int[][] points) {
            /**
             * Use similar structure as LE_56_Merge_Intervals
             * !!! Must use Integer.compare(), otherwise using "a[0] - b[0] may cause integer overflow"
             */
            Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
            List<int[]> res = new ArrayList<>();

            int[] last = null;
            for (int[] cur : points) {
                if (last == null) {
                    last = cur;
                } else {
                    if (cur[0] > last[1]) {
                        res.add(last);
                        last = cur;
                    } else { //"<="
                        last = new int[]{Math.max(cur[0], last[0]), Math.min(cur[1], last[1])};
                    }
                }
            }

            res.add(last);//!!!

            return res.size();
        }
    }
}
