package leetcode;

import java.util.*;

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
         What if there are lots of merges and the number of disjoint intervals are small
         compared to the data stream's size?

         Hard
     **/

    /**
         Possible variation?
         "给一个Interval class:
         class Interval {int start, int end}
         让我写两个function: add(int start, int end) : 建立一个新的interval类，然后存到自己定义的数据结构上，下面那个function会用到
         get_total(int start, int end):  给出start,和 end之间的所有interval覆盖的长度 （重复覆盖的部分只能算一次）"

         Variation:
         给一顿zip code的区间，要新插入一个zip code，临近的话合并，不临近的话就单独生成一个自己的区间
     **/

    /**
     * Summary :
     * Solution1
     * Use TreeMap, has balanced performance , O(logn) for both addNum() and getIntervals()
     *
     * Solution3
     * Optimized for addNum() - O(1), getIntervals() is O(logn). It is for follow up -
     * "What if there are lots of merges and the number of disjoint intervals are small
     * compared to the data stream's size?"
     */

    /**
     *   TreeMap Solution  : O(logn) for addNum(), O(n) for getIntervals()
     *
         TreeMap<Integer, Interval> map
         add() : add new Interval to TreeMap.
         get_total(int start, int end) : merge all intervals between start and end, find total length;

         fromKey = TreeMap.higherKey(start) //first Interval
         toKey = TreeMap.higherKey(end)      //last Interval

         submap = TreeMap.subMap(fromKey, toKey)
         "Returns a view of the portion of this map whose keys range from fromKey, inclusive, to toKey, exclusive"

         Iterate through submap, do merge, same as in LE_56_Merge_Intervals

         94 ms, 63.41%
     */

    class SummaryRanges1 {
        /**
         * Key is start of the interval
         */
        TreeMap<Integer, Interval> map;

        /** Initialize your data structure here. */
        public SummaryRanges1() {
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
                 * 上下相邻的interval存在，和已知的上下interval连接，去掉上边interval,因为被merge了。
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
            //If asked to return total length
//            int res = 0;
//            for (Interval interval : map.values()) {
//                res += interval.end - interval.start + 1;
//            }
//            return res;

            return new ArrayList<>(map.values());
        }
    }

    class SummaryRanges_Practice {
        TreeMap<Integer, Interval> map;

        /** Initialize your data structure here. */
        public SummaryRanges_Practice() {
            map = new TreeMap<>();
        }

        public void addNum(int val) {
            if (map.containsKey(val)) {
                return;
            }

            /**
             * !!!
             * Must be Integer, not int,
             * otherwise we can not compare it with null
             */
            Integer hKey = map.higherKey(val);
            Integer lKey = map.lowerKey(val);

            /**
             * !!!
             * 坑 ：
             * 对于当前val, find lKey and hKey. 如果lKey找到，意味着又一个start值比val小的interval存在。
             * 但是，这个interval的终点(end)有3中情况：
             *  1.end < val
             *  2.end == val
             *  3.end > val
             *
             * 我们合并2和3的处理，"val - 1 <= map.get(lKey).end"，对于2，val应该和和这个interval合并。
             * 对于3，interval不用改变。
             *
             *
             */
            if (hKey != null && lKey != null && val + 1 == hKey  && val - 1 == map.get(lKey).end) {
                map.get(lKey).end = map.get(hKey).end;
                map.remove(hKey);
            } else if (hKey != null && val + 1 == hKey) {
                map.put(val, new Interval(val, map.get(hKey).end));
                map.remove(hKey);
            } else if (lKey != null && val - 1 <= map.get(lKey).end) {
                map.get(lKey).end = Math.max(val, map.get(lKey).end);
            } else {
                map.put(val, new Interval(val, val));
            }
        }

        public List<Interval> getIntervals() {
            return new ArrayList<Interval>(map.values());
        }
    }

    /**
     * A Better solution, addNum() is O(1)
     *
     *
     */
    class Solution2 {
        // Key - left or right boundary value of range, Value - size of range
        private Map<Integer, Integer> ranges = new HashMap<>();

        // Since middle val is removed, an extra set is required to de-duplicate
        private Set<Integer> dup = new HashSet<>();

        public void addNum(int val) {
            if (!dup.add(val)) {
                return;
            }

            int left = ranges.containsKey(val - 1) ? ranges.remove(val - 1) : 0;
            int right = ranges.containsKey(val + 1) ? ranges.remove(val + 1) : 0;
            int sum = left + right + 1;

            if (left > 0) {
                ranges.put(val - left, sum);
            }
            if (right > 0) {
                ranges.put(val + right, sum);
            }
            if (left == 0 || right == 0) {
                ranges.put(val, sum); // remove middle val to speed up getInt()
            }
        }

        public List<Interval> getIntervals() {
            List<Interval> ret = new ArrayList<>();
            List<Integer> keys = new ArrayList<>(ranges.keySet());
            Collections.sort(keys);

            int last = Integer.MIN_VALUE;
            for (int left : keys) {
                int size = ranges.get(left);
                if (last < left) {
                    ret.add(new Interval(left, left + size - 1));
                    last = left + size - 1;
                }
            }
            return ret;
        }
    }

    /**
     * O(1) for add. Use two HashMaps
     *
     * According to the follow up, there are more adds than the number of intervals hence
     * the implementation. k is the number of intervals.
     */
    class Solution3 {
        class SummaryRanges {

            private Map<Integer, Integer> start; //Key : start, Value : end
            private Map<Integer, Integer> end;   //Key : end,   Value : start
            private Set<Integer> set;
            /** Initialize your data structure here. */
            public SummaryRanges() {
                start = new HashMap<>();
                end = new HashMap<>();
                set = new HashSet<>();
            }

            public void addNum(int val) {
                int m1 = val - 1;
                int p1 = val + 1;

                if (set.contains(val)) {
                    return;
                } else {
                    set.add(val);
                }

                if (start.containsKey(p1) && end.containsKey(m1)) {
                    int s = end.remove(m1);
                    int e = start.remove(p1);
                    start.put(s, e);
                    end.put(e, s);
                } else if (start.containsKey(p1)) {
                    int e = start.remove(p1);
                    start.put(val, e);
                    end.put(e, val);
                } else if (end.containsKey(m1)) {
                    int s = end.remove(m1);
                    end.put(val, s);
                    start.put(s, val);
                } else {
                    start.put(val, val);
                    end.put(val, val);
                }
            }

            public List<Interval> getIntervals() {
                List<Interval> res = new ArrayList<>();
                for (int i : start.keySet()) {
                    Interval inter = new Interval(i, start.get(i));
                    res.add(inter);
                }

                /**
                 * !!!
                 */
                Collections.sort(res, (i1, i2) -> i1.start - i2.start);

                return res;
            }
        }
    }

    /**
     * Java fast log (N) solution (186ms) without using the TreeMap but a customized BST
     *
     * Fast : 82 ms, 98.58%
     *
     * https://leetcode.com/problems/data-stream-as-disjoint-intervals/discuss/82610/Java-fast-log-(N)-solution-(186ms)-without-using-the-TreeMap-but-a-customized-BST
     */
    class Solution4 {
        public class SummaryRanges {
            class BSTNode {
                Interval interval;
                BSTNode left;
                BSTNode right;
                BSTNode(Interval in){
                    interval = in;
                }
            }

            BSTNode findMin(BSTNode root) {
                if (root == null) return null;
                if (root.left == null ) return root;
                else return findMin(root.left);
            }

            BSTNode remove(Interval x, BSTNode root) {
                if (root == null) return null;
                else if ( x == null ) return root;
                else if (x.start > root.interval.end ) {
                    root.right = remove(x, root.right);
                } else if (x.end < root.interval.start ) {
                    root.left = remove(x, root.left);
                } else if ( root.left != null && root.right != null) {
                    root.interval = findMin(root.right).interval;
                    root.right = remove( root.interval, root.right);
                } else {
                    root = ( root.left != null ) ? root.left : root.right;
                }
                return root;
            }

            BSTNode findKey(int val, BSTNode root) {
                if (root == null) return null;
                if (root.interval.start > val) {
                    return findKey(val, root.left);
                } else if (root.interval.end < val) {
                    return findKey(val, root.right);
                } else return root;
            }

            BSTNode addKey(int val, BSTNode root) {
                if (root == null) {
                    root = new BSTNode( new Interval(val, val) );
                } else if (root.interval.start > val) {
                    root.left = addKey(val, root.left);
                } else if (root.interval.end < val) {
                    root.right = addKey(val, root.right);
                }
                return root;
            }
            void inOrder(BSTNode root) {
                if (root != null) {
                    inOrder(root.left);
                    list.add(root.interval);
                    inOrder(root.right);
                }
            }

            /** Initialize your data structure here. */
            BSTNode root;
            List<Interval> list = new ArrayList();
            public SummaryRanges() {
                root = null;
            }

            public void addNum(int val) {
                if (root == null) {
                    root = addKey(val, root);
                } else {
                    if ( findKey(val, root) != null) return;
                    BSTNode left = findKey(val-1, root);
                    BSTNode right = findKey(val+1, root);
                    if (left == null && right == null) {
                        root = addKey(val, root);
                    } else if (left != null && right == null) {
                        left.interval.end++;
                    } else if (left == null && right != null) {
                        right.interval.start--;
                    } else {
                        Interval l = left.interval;
                        int e = right.interval.end;
                        root = remove(right.interval, root);
                        l.end = e;
                    }
                }
            }

            public List<Interval> getIntervals() {
                list.clear();
                inOrder(root);
                return list;
            }
        }
    }
}
