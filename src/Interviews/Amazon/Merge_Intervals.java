package Interviews.Amazon;

import common.Interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Merge_Intervals {
    /**
     * LE_56_Merge_Intervals
     */
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) {
            return new ArrayList<>();
        }

        Collections.sort(intervals, (a, b) -> a.start - b.start);

        Interval last = null;
        for (Interval item : intervals) {
            if (last == null || last.end < item.start) {
//                res.add(item);
                last = item;
            } else {
                last.end = Math.max(last.end, item.end);
            }
        }

        return intervals;
    }

    /**
     * Variation
     * Given list of intervals, require to operate on the input list so we use no extra space.
     *
     * What it tries to test is if you know how to use Iterator. We can't use for loop with index
     * because once we delete an item from list, the size of the list will be changed and the index
     * for the next item will not be the same as the one before the delete action.
     *
     * So we must use iterator.remove(), which removes from the underlying collection the last element
     * returned by this iterator, then we can still go to the next item correctly.
     */
    public List<Interval> merge1(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) {
            return new ArrayList<>();
        }

        Collections.sort(intervals, (a, b) -> a.start - b.start);

        Interval last = null;

        Iterator<Interval> it = intervals.iterator();

        while (it.hasNext()) {
            Interval cur = it.next();
            if (last == null || last.end < cur.start) {
                last = cur;
            } else {
                last.end = Math.max(last.end, cur.end);
                it.remove();
            }
        }

        return intervals;
    }
}
