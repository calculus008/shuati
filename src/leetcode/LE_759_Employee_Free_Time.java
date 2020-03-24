package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LE_759_Employee_Free_Time {
    /**
     * We are given a list schedule of employees, which represents the working time for each employee.
     *
     * Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
     *
     * Return the list of finite intervals representing common, positive-length free time for all employees,
     * also in sorted order.
     *
     * (Even though we are representing Intervals in the form [x, y], the objects inside are Intervals,
     * not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and
     * schedule[0][0][0] is not defined).  Also, we wouldn't include intervals like [5, 5] in our answer,
     * as they have zero length.
     *
     *
     *
     * Example 1:
     * Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
     * Output: [[3,4]]
     * Explanation: There are a total of three employees, and all common
     * free time intervals would be [-inf, 1], [3, 4], [10, inf].
     * (!!!)
     * We discard any intervals that contain inf as they aren't finite.
     *
     * Example 2:
     * Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
     * Output: [[5,6],[7,9]]
     *
     *
     * Constraints:
     *
     * 1 <= schedule.length , schedule[i].length <= 50
     * 0 <= schedule[i].start < schedule[i].end <= 10^8
     *
     * Hard
     */


    /**
     * Improve from Solution2, we don't need to merge and run another loop to find answer,
     * we can just find answer during k-way merge
     *
     * In theory, this is the optimize for both time and space.
     * The same as Soluion2 in official solutions
     * https://leetcode.com/problems/employee-free-time/solution/
     *
     * Time  : O(mnlogm)
     * Space : O(m)
     */
    class Solution2 {
        class Pair {
            int row;
            int col;

            public Pair(int row, int col) {
                this.row = row;
                this.col = col;
            }
        }

        public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
            List<Interval> res = new ArrayList<>();
            if (schedule == null || schedule.size() == 0) return res;

            PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> schedule.get(a.row).get(a.col).start - schedule.get(b.row).get(b.col).start);

            for (int i = 0; i < schedule.size(); i++) {
                if (!schedule.get(i).isEmpty()) {
                    pq.offer(new Pair(i, 0));
                }
            }

            Pair top = pq.peek();
            Interval pre = schedule.get(top.row).get(top.col);

            while (!pq.isEmpty()) {
                Pair p = pq.poll();
                Interval cur = schedule.get(p.row).get(p.col);

                if (pre.end < cur.start) {
                    res.add(new Interval(pre.end, cur.start));
                    pre = cur;
                } else {
                    pre.end = Math.max(pre.end, cur.end);
                }

                if (p.col + 1< schedule.get(p.row).size()) {
                    p.col++;
                    pq.offer(p);
                }
            }

            return res;
        }
    }

    /**
     * Simplest solution, just use List<Interval>
     *
     * Not the optimized solution, but it's the fastest one on leetcode.
     *
     * Time : O(mnlog(mn))
     * Space : O(mn)
     */
    class Solution3 {
        public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
            List<Interval> res = new ArrayList<>();
            if (schedule == null || schedule.size() == 0) return res;

            List<Interval> list = new ArrayList<>();

            for (List<Interval> l : schedule) {
                list.addAll(l);
            }

            Collections.sort(list, (a, b) -> a.start - b.start);

            Interval pre = list.get(0);

            for (Interval interval : list) {
                if (pre.end < interval.start) {
                    res.add(new Interval(pre.end, interval.start));
                    pre = interval;
                } else {
                    pre.end = Math.max(pre.end, interval.end);
                }
            }

            return res;
        }
    }

    /**
     * Time : O(mnlogm + mn)
     * Space : O(mn)
     */
    class Solution {
        class Pair {
            int row;
            int col;

            public Pair(int row, int col) {
                this.row = row;
                this.col = col;
            }
        }

        public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
            List<Interval> res = new ArrayList<>();
            if (schedule == null || schedule.size() == 0) return res;

            /**
             * #1.K-way merge intervals
             */
            PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> schedule.get(a.row).get(a.col).start - schedule.get(b.row).get(b.col).start);

            for (int i = 0; i < schedule.size(); i++) {
                if (!schedule.get(i).isEmpty()) {
                    pq.offer(new Pair(i, 0));
                }
            }

            List<Interval> ans = new ArrayList<>();
            Pair top = pq.peek();
            Interval pre = schedule.get(top.row).get(top.col);

            while (!pq.isEmpty()) {
                Pair p = pq.poll();
                Interval cur = schedule.get(p.row).get(p.col);

                if (pre.end < cur.start) {
                    ans.add(pre);
                    pre = cur;
                } else {
                    pre.end = Math.max(pre.end, cur.end);
                }

                if (p.col + 1< schedule.get(p.row).size()) {
                    p.col++;
                    pq.offer(p);
                }
            }

            ans.add(pre);

            /**
             * if it requires to add -INF to start time
             */
            // if (!ans.isEmpty() && ans.get(0).start > Integer.MIN_VALUE) {
            //     res.add(new Interval(Integer.MIN_VALUE, ans.get(0).start));
            // } else {
            //     res.add(new Interval(Integer.MIN_VALUE, Integer.MAX_VALUE));
            //     return res;
            // }

            /**
             * #2.Find free time
             */
            Interval last = ans.get(0);
            for (int i = 1; i < ans.size(); i++) {
                Interval cur = ans.get(i);
                res.add(new Interval(last.end, cur.start));
                last = cur;
            }

            /**
             * if it requires to add end time to INF
             */
            // if (last.end != Integer.MAX_VALUE) {
            //     res.add(new Interval(last.end, Integer.MAX_VALUE));
            // }

            return res;
        }
    }
}
