package leetcode;

import sun.jvm.hotspot.utilities.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 3/3/18.
 */
public class LE_56_Merge_Intervals {
    /**
        Given a collection of intervals, merge all overlapping intervals.

        For example,
        Given [1,3],[2,6],[8,10],[15,18],
        return [1,6],[8,10],[15,18].
     */

    public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    /**
     * Best version, concise, short and easy to remember
     */
    class Solution1 {
        public List<Interval> merge(List<Interval> intervals) {
            List<Interval> res = new ArrayList<>();
            if (intervals == null || intervals.size() == 0) {
                return res;
            }

            Collections.sort(intervals, (a, b) -> a.start - b.start);

            Interval last = null;
            for (Interval item : intervals) {
                if (last == null || last.end < item.start) {
                    /**
                     * !!!
                     * add item, not last
                     **/
                    res.add(item);

                    /**
                     * Key point - Utilize Java characteristic - pass by reference,
                     * here we set last point to item, at this moment, item is already
                     * added to res, in the each iteration, we check if current interval
                     * has overlapping part with last, if yes, update end.
                     *
                     * In other words, we first add item, then updating end value in each
                     * iteration if current interval has overlapping part with item.
                     */
                    last = item;
                } else {
                    /**
                     * Since we already sort intervals based on start value, start value
                     * here must be the min value, so we don't need to worry about it.
                     * We just need to keep updating end value.
                     */
                    last.end = Math.max(last.end, item.end);
                }
            }

            return res;
        }
    }

    class Solution1_New_Input_Type {
        public int[][] merge(int[][] intervals) {
            if (intervals.length <= 1) return intervals;

            Arrays.sort(intervals, (i1, i2) -> i1[0] - i2[0]);

            int m = intervals.length;
            List<int[]> res = new ArrayList<>();
            int[] last = null;

            for (int[] interval : intervals) {
                if (last == null || last[1] < interval[0]) {
                    res.add(interval);
                    last = interval;
                } else {
                    last[1] = Math.max(last[1], interval[1]);
                }
            }

            /**
             * !!!
             */
            return res.toArray(new int[res.size()][]);
        }
    }

    //Time : O(nlogn) (use sorting), Space : O(n)
    class Solution2 {
        public List<Interval> merge(List<Interval> intervals) {
            /**
             * !!!
             * "<= 1", "return intervals"
             **/
            if (intervals == null || intervals.size() <= 1) return intervals;

            /**!!!*/
            Collections.sort(intervals, (a, b) -> a.start - b.start);

            /**
             * global start and end
             */
            int start = intervals.get(0).start;
            int end = intervals.get(0).end;
            List<Interval> res = new ArrayList<>();

            for (Interval interval : intervals) {
                /**
                 * !!!
                 * "<= end" -> case : [[1,4],[4,5]], after merge : [1,5]
                 **/
                if (interval.start <= end) {
                    end = Math.max(interval.end, end);
                } else {
                    res.add(new Interval(start, end));
                    start = interval.start;
                    end = interval.end;
                }
            }

            /**!!!
             * 千万不要忘了加最后一个
             **/
            res.add(new Interval(start, end));

            return res;
        }
    }

}
