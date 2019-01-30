package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by yuank on 5/21/18.
 */
public class LE_352_Data_Stream_As_Disjoint_Intervals {
    /**
         Given a data stream input of non-negative integers a1, a2, ..., an, ...,
         summarize the numbers seen so far as a list of disjoint intervals.

         For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:

         [1, 1]
         [1, 1], [3, 3]
         [1, 1], [3, 3], [7, 7]
         [1, 3], [7, 7]
         [1, 3], [6, 7]

         Follow up:
         What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?

         Hard
     */

    class SummaryRanges {
        /**
         * Key is start of the interval
         */
        TreeMap<Integer, Interval> map;

        /** Initialize your data structure here. */
        public SummaryRanges() {
            map = new TreeMap<>();
        }

        /**
         * Time : O(logn), TreeMap is implemented as Red-Black tree, remove and add takes O(logn)
         **/
        public void addNum(int val) {
            /**
             !!!
             **/
            if (map.containsKey(val)) {
                return;
            }

            /**
             * !!!
             * lowerKey() : Returns the greatest key strictly less than the given key, or null if there is no such key.
             * floorKey() : Returns the greatest key less than OR EQUAL TO the given key, or null if there is no such key.
             *
             * 所以，这里只能用lowerKey/higherKey, 不能用ceilingKey/floorKey(含等于的情况）
             */
            Integer lowerKey = map.lowerKey(val);
            Integer higherKey = map.higherKey(val);

            /**
                 关键：
                 1.最后一个“else",不只是lowerKey和higherKey都为null, 它其实是代表所有必须加入[val, val]的情况。
                 也就是说，所有可能的interval merge的情况都已经被前面的if分支处理了。
                 所以，前面的if条件必须那样写。
                 2.TreeMap中的key是interval里的start值。
             **/
            if (lowerKey != null && higherKey != null && map.get(lowerKey).end + 1 == val && val == map.get(higherKey).start - 1) {
                /**
                 * 上下相邻的interal存在，和已知的上下interval连接，去掉上边interval,因为被merge了。
                 */
                map.get(lowerKey).end = map.get(higherKey).end;
                map.remove(higherKey);
            } else if (lowerKey != null && val <= map.get(lowerKey).end + 1) {
                /**
                 * 只有下边的相邻interval存在，连接
                 */
                map.get(lowerKey).end = Math.max(val, map.get(lowerKey).end);
            } else if (higherKey != null && val + 1 == map.get(higherKey).start) {
                /**
                 * 只有上边的相邻interval存在，连接， 去掉上边interval,因为被merge了
                 */
                map.put(val, new Interval(val, map.get(higherKey).end));
                map.remove(higherKey);
            } else {
                /**
                 * 没有上下相邻interval，
                 */
                map.put(val, new Interval(val, val));
            }
        }

        public List<Interval> getIntervals() {
            return new ArrayList<>(map.values());
        }
    }
}
