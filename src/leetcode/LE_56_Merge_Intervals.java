package leetcode;

import sun.jvm.hotspot.utilities.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 3/3/18.
 */
public class LE_56_Merge_Intervals {
    /*
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

    //Time : O(nlogn) (use sorting), Space : O(n)
    class Solution {
        public List<Interval> merge(List<Interval> intervals) {
            //!!! "<= 1", "return intervals"
            if (intervals == null || intervals.size() <= 1) return intervals;

            Collections.sort(intervals, (a, b) -> a.start - b.start);
            int start = intervals.get(0).start;
            int end = intervals.get(0).end;
            List<Interval> res = new ArrayList<>();

            for (Interval interval : intervals) {
                //!!! "<= end" -> case : [[1,4],[4,5]], after merge : [1,5]
                if (interval.start <= end) {
                    end = Math.max(interval.end, end);
                } else {
                    res.add(new Interval(start, end));
                    start = interval.start;
                    end = interval.end;
                }
            }

            //!!!千万不要忘了加最后一个
            res.add(new Interval(start, end));

            return res;
        }
    }
}
