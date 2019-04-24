package Linkedin;

import common.Interval;

import java.util.*;

public class Merge_Intervals {
    /**
     * 印度小哥 Coding. Merge Intervals 写两个函数 addInterval, getTotalBusyTime。写出两种不同的实现
     * 分析trade off(基本就是根据两个函数的调用频率决定)
     *
     * (1) LinkedList 插入使得每次插入后start保持有序并保持所有的节点都是disjointed 同时计算totalbusy。
     *     O(N)的add时间和O(1)get时间
     * (2) Binary search tree. 也就是map，treemap这种。 保持插入后有序，O(logN) add O(N) get时间
     *
     * follow up 如果需要remove interval 用哪种方式？
     *
     * http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=144989&extra=page%3D7%26filter%3Dsortid%26sortid%3D311%26searchoption%5B3046%5D%5Bvalue%5D%3D6%26searchoption%5B3046%5D%5Btype%5D%3Dradio%26sortid%3D311
     *
     * Similar to LE_352_Data_Stream_As_Disjoint_Intervals, input is different, here it is interval,
     * for 352, it is disjoint numbers.
     */

    /**
     * time complexity: add() - O(n), get() - O(1)
     *
     * For case that has lot of get() but small number of add()
     * **/
    class MergeIntervals_1 {
        Node head = new Node(0, 0);
        int totalLength = 0;

        /**
         * Merge while add and calculate totalLen
         */
        public void add(int start, int end) {
            if (start >= end) {
                return;
            }

            Node runner = head.next;
            Node prev = head;
            while (runner != null) {
                if (end < runner.start) {//find insertion point, insert
                    Node node = new Node(start, end);
                    runner.pre.next = node;
                    node.pre = runner.pre;
                    node.next = runner;
                    runner.pre = node;
                    totalLength += end - start;
                    break;
                } else if (start > runner.end) {//try to locate the insert point, keep moving forward
                    prev = runner;
                    runner = runner.next;
                } else {
                    totalLength -= runner.end - runner.start;
                    start = Math.min(start, runner.start);
                    end = Math.max(end, runner.end);
                    Node next = runner.next;
                    runner.pre.next = runner.next;
                    if (runner.next != null) {
                        runner.next.pre = runner.pre;
                    }
                    runner.next = null;
                    runner.pre = null;
                    runner = next;
                }
            }

            if (runner == null) {
                totalLength += end - start + 1;
                prev.next = new Node(start, end);
                prev.next.pre = prev;
            }
        }

        public int getTotalLength() {
            return totalLength;
        }

        /**
         * DLL Node
         */
        class Node {
            Node pre = null;
            Node next = null;

            int start;
            int end;

            public Node(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }

    class MergeIntervals_2 {
        /**
         * Another way to do insert : Use logic in LE_57_Insert_Interval
         * As we keep inserting, intervals saved in list should already in sorted order.
         * We need to calculate totalLen when we insert
         *
         * Time : O(n)
         */
        List<Interval> intervals;
        int totalLength = 0;

        /**
         * O(n)
         */
        public void add(int start, int end) {
            Interval newInterval = new Interval(start, end);

            List<Interval> res = new ArrayList<>();
            totalLength = 0;
            int i = 0;

            while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
                Interval cur = intervals.get(i);
                totalLength += cur.end - cur.start + 1;
                res.add(cur);
                i++;
            }

            while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
                newInterval.start = Math.min(intervals.get(i).start, newInterval.start);
                newInterval.end = Math.max(intervals.get(i).end, newInterval.end);
                i++;
            }

            totalLength += newInterval.end - newInterval.start + 1;
            res.add(newInterval);

            while (i < intervals.size()) {
                Interval cur = intervals.get(i);
                totalLength += cur.end - cur.start + 1;
                res.add(cur);
                i++;
            }

            intervals = res;
        }

        /**
         * O(1)
         */
        public int getTotalLength() {
            return totalLength;
        }
    }


    /**
     * time complexity: add O(1), get O(nlgn)
     *
     * For scenarios that has lot of add(), relatively small number of get()
     **/
    class MergeIntervalWithLessGetCall {
        List<Interval> intervals = new ArrayList<>();

        /**
         * O(1)
         * Simply add to a list.
         */
        public void add(int start, int end) {
            if (start >= end) {
                return;
            }
            intervals.add(new Interval(start, end));
        }

        /**
         * O(nlogn)
         * First sort, then merge, similar to LE_56_Merge_Intervals,
         * difference is that it needs to return total intervals length
         */
        public int getTotalLength() {
            Collections.sort(intervals, (a, b) -> a.start - b.start);
            int totalLen = 0;

            int start = intervals.get(0).start;
            int end = intervals.get(0).end;

            for (Interval inter : intervals) {
                if (end < inter.start) {
                    totalLen += end - start + 1;

                    end = inter.end;
                    start = inter.start;
                } else {
                    end = Math.max(inter.end, end);
                }
            }

            /**
             * one more, don't forget
             */
            totalLen += end - start + 1;

            return totalLen;
        }
    }

    // Above is based on call frequency. what about using TreeSet, which could sort as well.
    class IntervalTreeSet {
        // Here we can use TreeSet to sort based on start time/end time
        TreeSet<Interval> treeSet = new TreeSet<Interval>((a, b) ->
            a.start != b.start ? a.start - b.start : a.end - b.end
        );

        /**
         * O(logn)
         * This is good for remove interval, which takes O(logn)
         */
        void add(int start, int end) {
            if (start > end) {
                return;
            }

            treeSet.add(new Interval(start, end)); // O(NLog(N))
        }

        /**
         * O(n)
         * Compare with last solution, since we use TreeSet, we don't need to
         * sort intervals before merging and calculating total length
         */
        int getLength() {
            int totalLen = 0;

            Iterator<Interval> it = treeSet.iterator();
            if (!it.hasNext()) {
                return 0;
            }

            Interval cur = it.next();

            int curStart = cur.start;
            int curEnd = cur.end;

            while (it.hasNext()) {
                Interval next = it.next();
                if (next.start > curEnd) {
                    // We can change this place to get Square from API, using Interval(curStart, curEnd)
                    totalLen += curEnd - curStart + 1;
                    curStart = next.start;
                    curEnd = next.end;
                } else {
                    curEnd = Math.max(curEnd, next.end);
                }
            }

            totalLen += curEnd - curStart + 1;

            return totalLen;
        }
    }

}
