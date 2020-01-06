package Interviews.Linkedin;

import common.Interval;

import java.util.*;

public class Merge_Intervals_Pratice {
    /**
     * query friendly, add O(n), getLength O(1)
     */
    class Solution1 {
        int total_length;
        List<Interval> list;

        public void add(int start, int end) {
            if (start > end) return;

            total_length = 0;
            list = new ArrayList<>();

            int i = 0;
            int size = list.size();

            List<Interval> res = new ArrayList<>();

            while (i < size && list.get(i).end < start) {
                Interval cur = list.get(i);
                total_length += cur.end - cur.start + 1;
                res.add(cur);
                i++;
            }

            while (i < size && list.get(i).start <= end) {
                start = Math.min(start, list.get(i).start);
                end = Math.max(end, list.get(i).end);
                i++;
            }

            total_length += end - start + 1;
            res.add(new Interval(start, end));

            while (i < size) {
                Interval cur = list.get(i);
                total_length += cur.end - cur.start + 1;
                res.add(cur);
                i++;
            }

            list = res;
        }

        public int getLlength() {
            return total_length;
        }
    }

    /**
     * add friendly, add O(1), get O(nlogn)
     */
    class Solution2 {
        List<Interval> list;

        public void add(int start, int end) {
            if (start >= end) return;

            list.add(new Interval(start, end));
        }

        public int getLlength() {
            int total_length = 0;

            Collections.sort(list, (a, b) -> a.start != b.start ? a.start - b.start : a.end - b.end);

            if (list.size() == 0) return 0;

            int start = list.get(0).start;
            int end = list.get(0).end;

            for (Interval interval : list) {
                if (end < interval.start) {
                    total_length += end - start + 1;
                } else {
                    end = Math.max(end, interval.end);
                }
            }

            /**
             * !!!
             */
            total_length += end - start + 1;

            return total_length;
        }
    }

    /**
     * add O(logn), getLength O(n)
     */
    class Solution3 {
        TreeSet<Interval> intervals = new TreeSet<>((a, b) -> a.start != b.start ? a.start - b.start : a.end - b.end);

        public void add(int start, int end) {
            if (start >= end) return;

            intervals.add(new Interval(start, end));
        }

        public int getLength() {
            if (intervals.size() == 0) return 0;

            int total_length = 0;

            Iterator<Interval> it = intervals.iterator();
            Interval first = it.next();
            int start = first.start;
            int end = first.end;

            while (it.hasNext()) {
                Interval cur = it.next();
                if (end < cur.start) {
                    total_length += end - start + 1;
                } else {
                    end = Math.max(end, cur.end);
                }
            }

            /**
             * !!!
             */
            total_length = end - start + 1;

            return total_length;
        }
    }
}
