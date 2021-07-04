package leetcode;

import java.util.Arrays;
import java.util.TreeMap;

public class LE_850_Rectangle_Area_II {
    /**
     * We are given a list of (axis-aligned) rectangles. Each rectangle[i] = [xi1, yi1, xi2, yi2] , where (xi1, yi1)
     * are the coordinates of the bottom-left corner, and (xi2, yi2) are the coordinates of the top-right corner of
     * the ith rectangle.
     *
     * Find the total area covered by all rectangles in the plane. Since the answer may be too large, return it modulo 109 + 7.
     *
     * Example 1:
     * Input: rectangles = [[0,0,2,2],[1,0,2,3],[1,0,3,1]]
     * Output: 6
     * Explanation: As illustrated in the picture.
     *
     * Example 2:
     * Input: rectangles = [[0,0,1000000000,1000000000]]
     * Output: 49
     * Explanation: The answer is 1018 modulo (109 + 7), which is (109)2 = (-7)2 = 49.
     *
     * Constraints:
     * 1 <= rectangles.length <= 200
     * rectanges[i].length = 4
     * 0 <= rectangles[i][j] <= 109
     * The total area covered by all rectangles will never exceed 263 - 1 and thus will fit in a 64-bit signed integer.
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/rectangle-area-ii/discuss/188832/Java-Line-Sweep-With-Sub-Class-Interval
     *
     * 1.order every line from the rectangle by y index. mark start of rectangle line (bottom) as OPEN, mark end
     *   of rectangle line (top) as CLOSE.
     * 2.sweep line from bottom to top, each time y coordinate changed, means all intervals on current y is sweeped,
     *   merge the length back together, multiply by the y coordinate diff.
     *
     * TreeMap:
     * Dynamically add/remove intervals on x, then calculate the total MERGED(!!!) length of x edge.
     *
     * Time Complexity:
     *                  O(NlogN + N * (N + logN)) = O(NlogN + N ^ 2 + NlogN) :
     *                  NlogN - sorting events
     *                  For each of rectangle : N - calculateInterval()
     *                                          NlogN - addInterval()/removeInterval()
     *
     * Space Complexity: O(N)
     */
    class Solution {

        private static class Interval {
            public int start;
            public int end;
            public Interval(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }

        public int rectangleArea(int[][] rectangles) {
            int OPEN = 0, CLOSE = 1;
            int[][] events = new int[rectangles.length * 2][4];

            int t = 0;
            /**
             open of rectangle: add to active set
             close of rectangle: remove from active set
             */
            for (int[] rec: rectangles) {
                // y, open_or_close, start, end (start and end index on x axis)
                events[t++] = new int[]{ rec[1], OPEN, rec[0], rec[2] };
                events[t++] = new int[]{ rec[3], CLOSE, rec[0], rec[2] };
            }

            /**
             sort by current y index
             */
            Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

            /**
             * intervals on x is sorted by using TreeMap
             */
            TreeMap<Interval, Integer> active = new TreeMap<>((a, b) -> {
                if (a.start != b.start) return a.start - b.start;
                return a.end - b.end;
            });

            // first y coordinate at the bottom
            int currentY = events[0][0];
            long ans = 0;
            for (int[] event : events) {
                int y = event[0], type = event[1], x1 = event[2], x2 = event[3];

                // Calculate sum of intervals in active set, that's the active intervals in prev line
                if (y > currentY) {
                    ans += calculateInterval(active) * (y - currentY);
                    currentY = y;
                }

                /**
                 add or remove new interval to current active
                 */
                if (type == OPEN) {
                    addInterval(active, x1, x2);
                } else {
                    removeInterval(active, x1, x2);
                }
            }
            ans %= 1_000_000_007;
            return (int) ans;
        }

        /**
         using tree map, should be able to insert in logn time
         */
        private void addInterval(TreeMap<Interval, Integer> map, int x1, int x2) {
            Interval interval = new Interval(x1, x2);
            map.put(interval, map.getOrDefault(interval, 0) + 1);
        }

        /**
         using tree map, should be able to remove in logn time
         */
        private void removeInterval(TreeMap<Interval, Integer> map, int x1, int x2) {
            Interval interval = new Interval(x1, x2);
            map.put(interval, map.getOrDefault(interval, 0) - 1);
            /**
             * !!!
             */
            if (map.get(interval) == 0) map.remove(interval);
        }

        /**
         * !!!
         * Calculate merged intervals total length with one pass, instead of one pass to merge and one pass to do sum.
         * Since keySet() is sorted, the logic deals with 3 relationship between current interval and previous interval.
         *
         * |_______| |________|  :  on overlap
         *
         * |_______|             : contained
         *  |___|
         *
         * |_______|             : partial overlap
         *   |_______|
         */
        private long calculateInterval(TreeMap<Interval, Integer> map) {
            long length = 0;
            int curPos = -1;
            for (Interval interval : map.keySet()) {
                curPos = Math.max(curPos, interval.start);
                length += Math.max(interval.end - curPos, 0);
                curPos = Math.max(curPos, interval.end);
            }
            return length;
        }

    }
}
