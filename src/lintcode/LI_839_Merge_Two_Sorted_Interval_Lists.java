package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 9/20/18.
 */
public class LI_839_Merge_Two_Sorted_Interval_Lists {
    /**
         Merge two sorted (ascending) lists of interval and return it as a new sorted list.
         The new sorted list should be made by splicing together the intervals of the two
         lists and sorted in ascending order.

         Example
         Given list1 = [(1,2),(3,4)] and list2 = [(2,3),(5,6)], return [(1,4),(5,6)].

         Notice
         The intervals in the given list do not overlap.
         The intervals in different lists may overlap.

         Easy
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
     * A better solution, shorter and logic is consistent with LE_56_Merge_Intervals
     *
     * "pre" is the same as "last" in LE_56_Merge_Intervals, it can be seen as the candidate
     * that will be added to the final answer.
     *
     * Basic logic:
     * When pre has no overlap with the current interval (from list1 or list2, depending on start value),
     * we can insert it into result list. Then pre will point to current interval.
     *
     * If pre has overlap with current interval, need to update its end value.
     */
    public class Solution3 {
        /**
         * @param list1: one of the given list
         * @param list2: another list
         * @return: the new sorted list of interval
         */
        public List<Interval> mergeTwoInterval(List<Interval> list1, List<Interval> list2) {
            List<Interval> res = new ArrayList<>();
            if (list1 == null && list2 == null) return res;
            if (list1 == null || list1.size() == 0) return list2;
            if (list2 == null || list2.size() == 0) return list1;

            int l1 = 0;
            int l2 = 0;
            int m = list1.size();
            int n = list2.size();

            Interval pre = null;
            if (list1.get(0).start < list2.get(0).start) {
                pre = list1.get(0);
                l1++;
            } else {
                pre = list2.get(0);
                l2++;
            }

            while (l1 < m || l2 < n) {
                /**
                 * "l2 == n": when list2 already runs out.
                 */
                if (l2 == n || (l1 < m && list1.get(l1).start < list2.get(l2).start)) {
                    if (pre.end < list1.get(l1).start) {
                        res.add(pre);
                        pre = list1.get(l1);
                    } else {
                        pre.end = Math.max(pre.end, list1.get(l1).end);
                    }
                    l1++;
                } else {
                    if (pre.end < list2.get(l2).start) {
                        res.add(pre);
                        pre = list2.get(l2);
                    } else {
                        pre.end = Math.max(pre.end, list2.get(l2).end);
                    }
                    l2++;
                }
            }

            /**
             * !!!
             * Don't forget
             */
            res.add(pre);

            return res;
        }
    }

    /**
     * Solution 2
     * 实际上和 Solution 1一样，只是在last和cur的使用上有差别。
     */
    class Solution2 {
        public List<Interval> mergeTwoInterval2(List<Interval> list1, List<Interval> list2) {
            List<Interval> results = new ArrayList<>();
            if (list1 == null || list2 == null) {
                return results;
            }

            Interval last = null, cur = null;
            int i = 0, j = 0;
            while (i < list1.size() && j < list2.size()) {
                if (list1.get(i).start < list2.get(j).start) {
                    cur = list1.get(i);
                    i++;
                } else {
                    cur = list2.get(j);
                    j++;
                }

                last = merge(results, last, cur);
            }

            while (i < list1.size()) {
                last = merge(results, last, list1.get(i));
                i++;
            }

            while (j < list2.size()) {
                last = merge(results, last, list2.get(j));
                j++;
            }

            if (last != null) {
                results.add(last);
            }
            return results;
        }

        private Interval merge(List<Interval> results, Interval last, Interval cur) {
            if (last == null) {
                return cur;
            }

            if (cur.start > last.end) {
                results.add(last);
                return cur;
            }

            last.end = Math.max(last.end, cur.end);
            return last;
        }
    }

    /**
     * Solution 1
     * 完全套用"LE_56_Merge_Intervals"的解法，在该题中，list1和list2都已经是排好序的，所以不用再排序。
     * 这里，多了一步要merge两个list.
     **/
    class Solution1 {
        public List<Interval> mergeTwoInterval1(List<Interval> list1, List<Interval> list2) {
            List<Interval> res = new ArrayList<>();
            if (list1 == null || list2 == null) return res;
            if (list1.size() == 0) return list2;
            if (list2.size() == 0) return list1;

            Interval cur = null;
            int i = 0, j = 0;

            /**
             * last 要初始化为merged list中的第一个interval, 此处我们不知道谁是第一个，
             * 所以在开始while loop前要做以下判断。
             */
            if (list1.get(0).start < list2.get(0).start) {
                cur = list1.get(i++);
            } else {
                cur = list2.get(j++);
            }

            Interval last = new Interval(cur.start, cur.end);

            while (i < list1.size() && j < list2.size()) {
                if (list1.get(i).start < list2.get(j).start) {
                    cur = list1.get(i++);
                } else if (j < list2.size()) {
                    cur = list2.get(j++);
                }

                process(res, cur, last);
            }

            while (i < list1.size()) {
                cur = list1.get(i++);
                process(res, cur, last);
            }

            while (j < list2.size()) {
                cur = list2.get(j++);
                process(res, cur, last);
            }

            res.add(last);

            return res;
        }

        private void process(List<Interval> res, Interval cur, Interval last) {
            if (cur.start <= last.end) {//overlap
                last.end = Math.max(last.end, cur.end);
            } else {//no overlap
                /**
                 * last只是保存上一个处理的状态，也就是说，只有在当前interval和last没有overlap时，
                 * 才把last代表的interval加入res.然后，last的状态被设为当前interval的状态。
                 *
                 * 注意，这里，我们用的Interval是在同一个class中定义的，所以“last.start"和
                 * “last.end"才可能。否则，start和end必须是public属性的。
                 */
                res.add(new Interval(last.start, last.end));
                last.start = cur.start;
                last.end = cur.end;
            }
        }
    }
}
