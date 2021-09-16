package leetcode;

import java.util.*;

public class LE_1825_Finding_MK_Average {
    /**
     * You are given two integers, m and k, and a stream of integers. You are tasked to implement a data structure that
     * calculates the MKAverage for the stream.
     *
     * The MKAverage can be calculated using these steps:
     *
     * If the number of the elements in the stream is less than m you should consider the MKAverage to be -1. Otherwise,
     * copy the last m elements of the stream to a separate container.
     * Remove the smallest k elements and the largest k elements from the container.
     * Calculate the average value for the rest of the elements rounded down to the nearest integer.
     * Implement the MKAverage class:
     *
     * MKAverage(int m, int k) Initializes the MKAverage object with an empty stream and the two integers m and k.
     * void addElement(int num) Inserts a new element num into the stream.
     * int calculateMKAverage() Calculates and returns the MKAverage for the current stream rounded down to the nearest
     * integer.
     *
     * Example 1:
     * Input
     * ["MKAverage", "addElement", "addElement", "calculateMKAverage", "addElement", "calculateMKAverage", "addElement",
     * "addElement", "addElement", "calculateMKAverage"]
     * [[3, 1], [3], [1], [], [10], [], [5], [5], [5], []]
     * Output
     * [null, null, null, -1, null, 3, null, null, null, 5]
     *
     * Explanation
     * MKAverage obj = new MKAverage(3, 1);
     * obj.addElement(3);        // current elements are [3]
     * obj.addElement(1);        // current elements are [3,1]
     * obj.calculateMKAverage(); // return -1, because m = 3 and only 2 elements exist.
     * obj.addElement(10);       // current elements are [3,1,10]
     * obj.calculateMKAverage(); // The last 3 elements are [3,1,10].
     *                           // After removing smallest and largest 1 element the container will be [3].
     *                           // The average of [3] equals 3/1 = 3, return 3
     * obj.addElement(5);        // current elements are [3,1,10,5]
     * obj.addElement(5);        // current elements are [3,1,10,5,5]
     * obj.addElement(5);        // current elements are [3,1,10,5,5,5]
     * obj.calculateMKAverage(); // The last 3 elements are [5,5,5].
     *                           // After removing smallest and largest 1 element the container will be [5].
     *                           // The average of [5] equals 5/1 = 5, return 5
     *
     *
     * Constraints:
     * 3 <= m <= 105
     * 1 <= k*2 < m
     * 1 <= num <= 105
     * At most 105 calls will be made to addElement and calculateMKAverage.
     *
     * Hard
     */

    /**
     * TreeMap + Queue
     *
     * Queue: it requires a "container" to store the LATEST m numbers. Queue natrually comes to mind because the it is
     *        FIFO.
     *
     * TreeMap:
     * It seems that it requires O(1) for calculateMKAverage(). So we need to maintain numbers in some kind of internal
     * data structure. We use 3 TreeMaps to store top k, middle and bottom k numbers. The heavy-lifting happens in
     * "addElement()", we need to dynamically adjust numbers between 3 TreeMaps.
     *
     * We can simulate the process of filling numbers into 3 TreeMap containers. We first put new number into top, if it
     * is
     */
    class MKAverage {
        TreeMap<Integer, Integer> top = new TreeMap<>();
        TreeMap<Integer, Integer> middle = new TreeMap<>();
        TreeMap<Integer, Integer> bottom = new TreeMap<>();

        Queue<Integer> q = new LinkedList<>();

        int m;
        int k;

        long sum;

        /**
         * MUST have those two counters to count the number of items, TreeMap.size() won't be helpful because we will
         * have duplicated items, size() only gives you number of entries in map.
         */
        int topSize;
        int bottomSize;

        public MKAverage(int m, int k) {
            this.m = m;
            this.k = k;
        }

        public void addElement(int num) {
            q.offer(num);

            /**
             * q has more than m items, we need to remove the oldest one, then we need to remove it from the TreeMap
             * it belongs to, then adjust and balance all 3 TreeMap.
             */
            if (q.size() > m) {//keep q size as m, remove the item from q and TreeMap.
                int item = q.poll();

                /**
                 * Based on where the removed item is, then:
                 * 1.Remove from that TreeMap
                 * 2.If it's in top or bottom, need to remove one item from middle and add to it.
                 *
                 * The purpose is to always maintain the same number of items in top and bottom as before the item is
                 * removed. Hence we don't need to adjust topSize and bottomSize here.
                 */
                if (!top.containsKey(item)) {//item not in top
                    if (!bottom.containsKey(item)) {//item not in bottom, must in middle
                        remove(middle, item);
                        sum -= item;
                    } else {//item in bottom
                        remove(bottom, item);

                        /**
                         * 找到middle的最小值，删除，调整sum, 把它加入bottom.
                         */
                        int middleFirstKey = middle.firstKey();
                        remove(middle, middleFirstKey);
                        sum -= middleFirstKey;
                        bottom.put(middleFirstKey, bottom.getOrDefault(middleFirstKey, 0) + 1);
                    }
                } else {//item in top
                    remove(top, item);

                    /**
                     * 找到middle的最大值，删除，调整sum, top.
                     */
                    int middleLastKey = middle.lastKey();
                    remove(middle, middleLastKey);
                    sum -= middleLastKey;
                    top.put(middleLastKey, top.getOrDefault(middleLastKey, 0) + 1);
                }
            }

            /**
             * !!!
             * Process of adding new number.
             *
             * First, add new number to top, if top's size is k, balance it by moving one item to bottom.
             * Then if bottom is over size k, balance it by moving one item to middle.
             */
            top.put(num, top.getOrDefault(num, 0) + 1);
            topSize++;

            if (topSize > k) {
                int topFirstKey = top.firstKey();
                remove(top, topFirstKey);
                topSize--;

                bottom.put(topFirstKey, bottom.getOrDefault(topFirstKey, 0) + 1);
                bottomSize++;

                if (bottomSize > k) {
                    int bottomLastKey = bottom.lastKey();
                    remove(bottom, bottomLastKey);
                    bottomSize--;

                    middle.put(bottomLastKey, middle.getOrDefault(bottomLastKey, 0) + 1);
                    sum += bottomLastKey;
                }
            }
        }

        public int calculateMKAverage() {
            if (q.size() < m) return -1;

            return (int)sum / (m - 2 * k);
        }

        private void remove(TreeMap<Integer, Integer> map, int key) {
            map.put(key, map.get(key) - 1);
            if (map.get(key) == 0) {
                map.remove(key);
            }
        }
    }
}
