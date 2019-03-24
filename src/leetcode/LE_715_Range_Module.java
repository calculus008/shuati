package leetcode;

import java.util.*;

public class LE_715_Range_Module {
    /**
     * A Range Module is a module that tracks ranges of numbers.
     * Your task is to design and implement the following interfaces
     * in an efficient manner.
     *
     * addRange(int left, int right) Adds the half-open interval [left, right),
     * tracking every real number in that interval. Adding an interval that
     * partially overlaps with currently tracked numbers should add any numbers
     * in the interval [left, right) that are not already tracked.
     * queryRange(int left, int right) Returns true if and only if every real
     * number in the interval [left, right) is currently being tracked.
     * removeRange(int left, int right) Stops tracking every real number
     * currently being tracked in the interval [left, right).
     *
     * Example 1:
     * addRange(10, 20): null
     * removeRange(14, 16): null
     * queryRange(10, 14): true (Every number in [10, 14) is being tracked)
     * queryRange(13, 15): false (Numbers like 14, 14.03, 14.17 in [13, 15) are not
     *                     being tracked)
     * queryRange(16, 17): true (The number 16 in [16, 17) is still being tracked,
     *                     despite the remove operation)
     *
     * Note:
     *
     * A half open interval [left, right) denotes all real numbers left <= x < right.
     * 0 < left < right < 10^9 in all calls to addRange, queryRange, removeRange.
     * The total number of calls to addRange in a single test case is at most 1000.
     * The total number of calls to queryRange in a single test case is at most 5000.
     * The total number of calls to removeRange in a single test case is at most 1000.
     *
     * Hard
     */

    /**
     * TreeMap
     */
    class RangeModule {
        TreeMap<Integer, Integer> intervals = new TreeMap<>();

        /**
         * addRange()可能的4种情况。Interval C is input
         * 1.
         * A  |_____________|      B |__________________|
         * C              |_____________|
         *
         * 2.
         * A  |_____________|
         * C              |_____________|
         *
         * 3.
         *                         B |__________________|
         * C              |_____________|
         *
         * 4.
         * C              |_____________|
         */

        public void addRange(int left, int right) {
            Integer start = intervals.floorKey(left);
            Integer end = intervals.floorKey(right);

            //#1 and #2, 确定left的位置。
            if(start != null && intervals.get(start) >= left){
                left = start;
            }

            //#1 and #3, 确定right的位置
            if(end != null && intervals.get(end) > right){
                right = intervals.get(end);
            }

            //隐含的，如果start == null and end == null, 则输入的left和right 不变。

            //left and right已经确定，放入TreeMap
            intervals.put(left, right);

            //清理所有start位置在（left, right]中的intervals， 因为已经被新的interval 覆盖。
            intervals.subMap(left, false, right, true).clear();
        }

        /**
         * removeRange()可能的4种情况。Interval C is input
         * 1.
         * A  |_______________________________|
         * C              |_____________|
         *    |___________|             |_____|
         *
         * 2.
         * A  |_____________|
         * C              |_____________|
         *
         *    |___________|
         *
         * 3.
         *                         B |__________________|
         * C              |_____________|
         *                              |_______________|
         *
         * 4.So no overlap, nothing to remove, do nothing.
         * C              |_____________|
         */
        public void removeRange(int left, int right) {
            Integer start = intervals.floorKey(left);
            Integer end = intervals.floorKey(right);

            //#1, #3
            if(end != null && intervals.get(end) > right){
                intervals.put(right, intervals.get(end));
            }

            //#1, #2
            if(start != null && intervals.get(start) > left){
                intervals.put(start, left);
            }
            intervals.subMap(left, true, right, false).clear();
        }

        public boolean queryRange(int left, int right) {
            Integer start = intervals.floorKey(left);
            if(start == null) {
                return false;
            }
            return intervals.get(start) >= right;
        }
    }

    class RangeModule_Practice {
        TreeMap<Integer, Integer> map = new TreeMap<>();

        public void addRange(int left, int right) {
            Integer start = map.floorKey(left);
            Integer end = map.floorKey(right);

            if (start != null && map.get(start) >= left) {
                left = start;
            }

            if (end != null && map.get(end) > right) {
                right = map.get(end);
            }

            map.put(left, right);

            /**
             * 左开右闭
             */
            map.subMap(left, false, right, true).clear();
        }

        public void removeRange(int left, int right) {
            Integer start = map.floorKey(left);
            Integer end = map.floorKey(right);

            if (end != null && map.get(end) > right) {
                map.put(right, map.get(end));
            }

            if (start != null && map.get(start) > left) {
                map.put(start, left);
            }

            /**
             * 左闭右开
             */
            map.subMap(left, true, right, false).clear();
        }

        public boolean queryRange(int left, int right) {
            Integer start = map.floorKey(left);
            if (start == null) {
                return false;
            }
            return map.get(start) >= right;
        }
    }

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */

    /**
     * TreeSet
     * https://leetcode.com/problems/range-module/solution/
     */
    class RangeModule1 {
        TreeSet<Interval> ranges;

        public RangeModule1() {
            ranges = new TreeSet();
        }

        public void addRange(int left, int right) {
            Iterator<Interval> itr = ranges.tailSet(new Interval(0, left - 1)).iterator();

            while (itr.hasNext()) {
                Interval iv = itr.next();
                if (right < iv.left) {
                    break;
                }

                left = Math.min(left, iv.left);
                right = Math.max(right, iv.right);
                itr.remove();
            }

            ranges.add(new Interval(left, right));
        }

        public boolean queryRange(int left, int right) {
            Interval iv = ranges.higher(new Interval(0, left));
            return (iv != null && iv.left <= left && right <= iv.right);
        }

        public void removeRange(int left, int right) {
            Iterator<Interval> itr = ranges.tailSet(new Interval(0, left)).iterator();
            ArrayList<Interval> todo = new ArrayList();
            while (itr.hasNext()) {
                Interval iv = itr.next();
                if (right < iv.left) break;
                if (iv.left < left) todo.add(new Interval(iv.left, left));
                if (right < iv.right) todo.add(new Interval(right, iv.right));
                itr.remove();
            }
            for (Interval iv: todo) ranges.add(iv);
        }
    }

    class Interval implements Comparable<Interval>{
        int left;
        int right;

        public Interval(int left, int right){
            this.left = left;
            this.right = right;
        }

        /**
         * sorted by right coordinates
         */
        public int compareTo(Interval that){
            if (this.right == that.right) {
                return this.left - that.left;
            }
            return this.right - that.right;
        }
    }

    class RangeModule2 {
        /**
         * TreeMap : Key - start coordinate, Value - end coordinate
         */
        TreeMap<Integer, Integer> map;

        public RangeModule2() {
            map = new TreeMap<>();
        }

        public void addRange(int left, int right) {
            if (right <= left) {
                return;
            }

            Integer start = map.floorKey(left);
            Integer end = map.floorKey(right);

            if (start == null && end == null) {
                /**
                 * No overlap with any existing intervals
                 */
                map.put(left, right);
            } else if (start != null && map.get(start) >= left) {
                map.put(start, Math.max(map.get(end), Math.max(map.get(start), right)));
            } else {
                map.put(left, Math.max(map.get(end), right));
            }

            // clean up intermediate intervals
            map.subMap(left, false, right, true).clear();
        }

        public void removeRange(int left, int right) {
            if (right <= left) {
                return;
            }

            Integer start = map.floorKey(left);
            Integer end = map.floorKey(right);
            if (end != null && map.get(end) > right) {
                map.put(right, map.get(end));
            }
            if (start != null && map.get(start) > left) {
                map.put(start, left);
            }

            // clean up intermediate intervals
            map.subMap(left, true, right, false).clear();
        }

        public boolean queryRange(int left, int right) {
            Integer start = map.floorKey(left);
            if (start == null) return false;
            return map.get(start) >= right;
        }
    }
}
