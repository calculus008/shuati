package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/3/18.
 */
public class LE_57_Insert_Interval {
    /**
        Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

        You may assume that the intervals were initially sorted according to their start times.

        Example 1:
        Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].

        Example 2:
        Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].

        This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
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

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        //!!!
        if(newInterval == null) return intervals;

        List<Interval> res = new ArrayList<>();
        int i = 0;

        /**
         * #1.Find insertion position
         */
        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
            res.add(intervals.get(i++));
        }

        // int start = newInterval.start , end = newInterval.end;

        /**
         * #2.Merge
         * !!!
         * "<= newInterval.end", case : [[1,5]], insert [0,1], if use "<", it won't be merged
         **/
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            //!!! changes value in newInterval itself, otherwise, won't work
            //case: [[1,2],[3,5],[6,7],[8,10],[12,16]], insert [4,9]
            //newInterval : [3, 9] after compare with [3,5]

            //Or
            // start = Math.min(intervals.get(i).start, start);
            // end = Math.max(intervals.get(i).end, end);

            newInterval.start = Math.min(intervals.get(i).start, newInterval.start);
            newInterval.end = Math.max(intervals.get(i).end, newInterval.end);
            i++;
        }

        /**
         * #3.Insert
         */
        res.add(newInterval);
        // res.add(new leetcode.Interval(start, end));

        /**
         * $4.Copy the rest
         */
        while (i < intervals.size()) {
            res.add(intervals.get(i++));
        }

        return res;
    }

    public List<Interval> insert_practice(List<Interval> intervals, Interval newInterval) {
        List<Interval> res = new ArrayList<>();
        if (intervals == null) return res;

        int n = intervals.size();

        int i = 0;
        while (i < n && intervals.get(i).end < newInterval.start) {
            res.add(intervals.get(i));
            i++;
        }

        while (i < n && intervals.get(i).start <= newInterval.end) {
            newInterval.start = Math.min(newInterval.start, intervals.get(i).start);
            newInterval.end = Math.max(newInterval.end, intervals.get(i).end);
            i++;
        }

        res.add(newInterval);

        while (i < n) {
            res.add(intervals.get(i));
            i++;
        }

        return res;
    }
}
