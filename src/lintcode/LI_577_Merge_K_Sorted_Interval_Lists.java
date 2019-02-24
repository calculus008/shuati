package lintcode;

import common.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by yuank on 9/21/18.
 */
public class LI_577_Merge_K_Sorted_Interval_Lists {
    /**
         Merge K sorted interval lists into one sorted interval list. You need to merge overlapping intervals too.

         Example
         Given

         [
         [(1,3),(4,7),(6,8)],
         [(1,2),(9,10)]
         ]
         Return

         [(1,3),(4,8),(9,10)]

         Medium
     */

    /**
     * 使用 PriorityQueue 的版本,假设每个数组长度为 n，一共 k 个数组。
     * 时间复杂度 O(nklogk)
     * 空间复杂度 O(k)
     *
     * Combination of LI_839_Merge_Two_Sorted_Interval_Lists and LI_486_Merge_K_Sorted_Arrays
     *
     * Use pq to ensure process interval in the order of its start value (each interval list is sorted)
     *
     */
    class Element{
        public int row;
        public int col;
        public Interval interval;

        public Element(int row,int col, Interval interval) {
            this.row = row;
            this.col = col;
            this.interval = interval;
        }
    }

    public List<Interval> mergeKSortedIntervalLists(List<List<Interval>> intervals) {
        List<Interval> res = new ArrayList<>();
        if (intervals == null || intervals.size() == 0) return res;

        PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.interval.start - b.interval.start);
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).size() != 0) {//!!!
                pq.offer(new Element(i, 0, intervals.get(i).get(0)));
            }
        }

        Interval cur = null;
        Interval last = pq.peek().interval;

        while (!pq.isEmpty()) {
            Element e = pq.poll();
            cur = e.interval;
            int row = e.row;
            int col = e.col;

            process(res, cur, last);

            if (col < intervals.get(row).size() - 1) {
                pq.offer(new Element(row, col + 1, intervals.get(row).get(col + 1)));
            }
        }

        res.add(last);
        return res;
    }

    private void process(List<Interval> res, Interval cur, Interval last) {
        if (cur.start <= last.end) {
            last.end = Math.max(last.end, cur.end);
        } else {
            res.add(new Interval(last.start, last.end));
            last.start = cur.start;
            last.end = cur.end;
        }
    }

    /**
     * Another version, only difference is that we don't save Interval in Element object,
     * Element1 only saves row and rol so that we can use it to retrieve the interval from intervals.
     *
     * This will save memory space, especially when number of intervals is huge. It takes longer time
     * to run, since it needs to retrieve interval from intervals.
     */
    class Element1{
        public int row;
        public int col;

        public Element1(int row,int col) {
            this.row = row;
            this.col = col;
        }
    };

    public List<Interval> mergeKSortedIntervalLists2(List<List<Interval>> intervals) {
        List<Interval> res = new ArrayList<>();
        if (intervals == null || intervals.size() == 0) return res;

        PriorityQueue<Element1> pq = new PriorityQueue<>((a, b) ->
                intervals.get(a.row).get(a.col).start - intervals.get(b.row).get(b.col).start);
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).size() != 0) {//!!!
                pq.offer(new Element1(i, 0));
            }
        }

        Interval cur = null;
        Element1 top = pq.peek();
        Interval last = intervals.get(top.row).get(top.col);

        while (!pq.isEmpty()) {
            Element1 e = pq.poll();
            int row = e.row;
            int col = e.col;
            cur = intervals.get(row).get(col);

            process(res, cur, last);

            if (col < intervals.get(row).size() - 1) {
                pq.offer(new Element1(row, col + 1));
            }
        }

        /**
         *!!!
         **/
        res.add(last);
        return res;
    }

}
