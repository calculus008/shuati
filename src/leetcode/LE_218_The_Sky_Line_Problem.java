package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/9/18.
 */
public class LE_218_The_Sky_Line_Problem {
    /**
        A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance.
        Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A),
        write a program to output the skyline formed by these buildings collectively (Figure B).

        Buildings  Skyline Contour
        The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and
        Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height.
        It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings
        are perfect rectangles grounded on an absolutely flat surface at height 0.

        For instance, the dimensions of all buildings in Figure A are recorded as:
        [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .

        The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ]
        that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last
        key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always
        has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.

        For instance, the skyline in Figure B should be represented as:
        [ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].

        Notes:

        The number of buildings in any input list is guaranteed to be in the range [0, 10000].
        The input list is already sorted in ascending order by the left x position Li.
        The output list must be sorted by the x position.
        There must be no consecutive horizontal lines of equal height in the output skyline.
        For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three
        lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]

        Hard
     */

    /**
        Very Important.

        扫描线(Line Sweep)
        关键的规律 ：
        按x坐标扫描，在x1, 碰到正方形的起始边，如果该边的高度h是到现在遇到的边中最高的，那它是一个key point,加入 k[x1, h].
                    在x2, 碰到正方形的终结边，要看第二高的边。
        https://www.youtube.com/watch?v=7AE-VCGEhtI

        http://zxi.mytechroad.com/blog/tree/leetcode-218-the-skyline-problem/

        1.Sorting by x, then special cases :
          when x values are equal - if entering : sort by height in reverse order
                                    if leaving  : sort by height
          Therefore, for entering point, use -h as value.

        2.Line sweep : all points sorted in a list. This is the data structure we will
                       operate on. PriorityQueue is just the tools to help us maintain
                       the current max height. Simple logic - add -h to pq when entering,
                                                            - remove h when leaving
        3.Line sweep : get cur max from pq, compare with pre, add to res if they are not equal.
     */

    /**
     * Solution 1 : Heap, Time : O(n ^ 2), Space : O(n)
     *
     * PriorityQueue.remove() takes O(n), if we want to optimize it, need to use HashHeap,
     * which uses HashMap to track each element in heap, then do swap and sift up/down
     * it takes o(logn). Just need to understand its mechanism. A reference implementation
     * from JiuZhang:
     * https://github.com/awangdev/LintCode/blob/master/Java/HashHeap.java
     *
     **/
    public List<int[]> getSkyline1(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();

        //Based on the question conditions, b[0] - start, b[1] - end, b[2] - H
        for (int[] b : buildings) {
            //!!! NOT "new int[2]{b[0], -b[2]}"
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1],  b[2]});
        }

        /**
            Sort height by x, if x is equal, by height. Since we make the entry height negative,
            if the exit line of rectangle A and the start line of rectangle B are at the same x,
            enter line of B will be in front of the exit line of A. So the event of the entering
            line will be processed first.

            This is the trick that helps to deal with special cases that entry/exit lines of
            different rectangles overlap.
        */
        Collections.sort(heights, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b -a);
        //!!!
        heap.add(0);

        /**
         * For input [1, 2, 1], [2, 3, 2], after sorting heights:
         * [1, -1], [2, -2], [2, 1], [3, 2]
         *
         * heap : 0
         * pre : 0
         * [1, -1] , enter line
         * heap : 1, 0
         * cur = 1, pre = 0 -> res : [[1, 1]], pre = 1
         *
         * [2, -1], enter line
         * heap : 2, 1, 0
         * cur = 2, pre = 1 -> res : [[1, 1], [2, 2]], pre = 2
         *
         * [2, 1], exit line
         * heap : 2, 0
         * cur = 2, pre = 2, do nothing
         * !!!
         *
         * [3, 2], exit line
         * heap : 0
         * cur = 0, pre = 2 -> res : [[1, 1], [2, 2], [3, 0]]
         *
         */
        int pre = 0; //height of the last added key point
        for(int[] h : heights) {
            if (h[1] < 0) {//Entry line
                heap.add(-h[1]);
            } else {//Exit line
                //Take time O(n)
                heap.remove(h[1]);
            }

            int cur = heap.peek(); //!!! peek(), NOT peak()
            if (pre != cur) {
                res.add(new int[]{h[0], cur});
                pre = cur;
            }
        }

        return res;
    }

    /**
     * Solution 2 : Use TreeMap (B-Tree), Time : O(nlogn), Space : O(n)
     **/
    public List<int[]> getSkyline2(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();

        for (int[] b : buildings) {
            /**
             * set height values as negative for entering event,
             * if two events are at the same x coordinate, they will
             * be sorted increasingly, so the smallest negative number
             * (the highest) will be at the front. This is for special cases:
             *
             * 1.Building A is leaving and B is entering at the same x coordinate
             *   and A' height is lower than B's height. We want to make sure
             *   only B's height is in res.
             *
             * 2.Multiple buildings leave at the same x coordinate, they have
             *   different height, we want to make sure '0' is put into res.
             *
             * The negative value here only for ordering purpose of the swipe line
             * order, when we put into TreeMap, it will be converted back to
             * positive value.
             *
             * This is to guarantee that the entering event will
             * be processed before leaving event.
             */
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1],  b[2]});
        }

        /**
         * if two elements has the same x coordinate, sort it by height (2nd element in array),
         * otherwise sort by x coordinate
         */
        Collections.sort(heights, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        /**!!!
         *  In TreeMap here, key is height, value is frequency of the height.(!!!)
           "Collections.reverseOrder()",. "<Integer, Integer>"
            In TreeMap, sort it in reverse order (max to min) by frequency, so that
            we can use dist.firstKey() to get the biggest height.
         **/
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        map.put(0, 1);

        /**
         * biggest height before processing current event
         */
        int pre = 0;

        for (int[] h : heights) {
            /**
             * Sweep line, first process current event, ADD for entering event,
             * REMOVE for leaving event.
             */
            if (h[1] < 0) {//ADD
                map.put(-h[1], map.getOrDefault(-h[1], 0) + 1);
            } else {//REMOVE
                int val = map.get(h[1]);
                if (val == 1) {
                    /**!!!
                     * 注意，必须删除count为0的元素。否则treemap不能对key重新排序。
                     * "remove" takes time O(logn), 这是这个解法比用heap快的地方。
                     * **/
                    map.remove(h[1]);
                } else {
                    map.put(h[1], val - 1);
                }
            }

            /**
             * !!!
             * TreeMap "firstKey()": this is to find the MAX
             *
             * cur : current biggest height, after current event is processed.
             */
            int cur = map.firstKey();
            if (pre != cur) {
                res.add(new int[]{h[0], cur});
                pre = cur;
            }
        }

        return res;
    }

    class Solution_TreeMap_Practice {
        public List<List<Integer>> getSkyline(int[][] buildings) {
            List<List<Integer>> res = new ArrayList<>();
            if (buildings == null || buildings.length == 0) return res;

            List<int[]> heights = new ArrayList<>();

            for (int[] b : buildings) {
                heights.add(new int[]{b[0], -b[2]});
                heights.add(new int[]{b[1], b[2]});
            }

            /**
             * !!!
             */
            Collections.sort(heights, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

            TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
            /**
             * !!!
             * Must pad it with 0, otherwise will run into exception when process
             * the first element.
             */
            map.put(0, 1);

            int pre = 0;

            for (int[] h : heights) {
                if (h[1] < 0) {//enter
                    map.put(-h[1], map.getOrDefault(-h[1], 0) + 1);
                } else {
                    if (map.get(h[1]) == 1) {
                        map.remove(h[1]);
                    } else {
                        map.put(h[1], map.get(h[1]) - 1);
                    }
                }

                /**
                 * !!!
                 * TreeMap.firstKey()
                 */
                int cur = map.firstKey();
                if (pre != cur) {
                    List<Integer> l = new ArrayList<>();
                    l.add(h[0]);
                    l.add(cur);
                    res.add(l);

                    /**
                     * !!!
                     */
                    pre = cur;
                }
            }

            return res;
        }
    }

    /**
     * TreeMap + PriorityQueue
     *
     * https://www.youtube.com/watch?v=tQiXaCT0ndE&t=1s
     * https://docs.google.com/document/d/11rPTObi5sl_HrIwyHHSFAgk3Vx07RvTVvPATv5LEAbc/edit#
     */
    class Solution {
        public List<List<Integer>> getSkyline(int[][] buildings) {
            /**
             * TreeMap: key -> x coordinate, value -> list of heights at current x coordinate
             * 这里TreeMap的用处不同于前一个解法，它只是用来保存timeline。
             * O(nlogn)
             */
            TreeMap<Integer, List<int[]>> map = new TreeMap<>();
            for (int[] b : buildings) {
                map.putIfAbsent(b[0], new ArrayList<>());
                map.putIfAbsent(b[1], new ArrayList<>());
                map.get(b[0]).add(b);
                map.get(b[1]).add(b);
            }

            PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[2] - a[2]);
            List<List<Integer>> res = new ArrayList<>();

            /**
             * Iterate through timeline in TreeMap, 每次拿出当前坐标所有的heights,
             * 然后根据event type (enter or leave), 用maxHeap排序。
             */
            for (int a : map.keySet()) {
                List<int[]> bs = map.get(a);

                /**
                 * 对比于solution1, 这里我们对同一坐标上的heights一起处理。这样不用
                 * 考虑solution1里的corner cases.
                 */
                for (int[] b : bs) {
                    /**
                     * "b[0] == a", this is entering event
                     */
                    if (b[0] == a) {
                        maxHeap.offer(b);
                    } else {
                        /**
                         * maxHeap() takes O(n), 所以时间复杂度不是最优。
                         */
                        maxHeap.remove(b);
                    }
                }

                if (maxHeap.size() == 0) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(a);
                    tmp.add(0);
                    res.add(tmp);
                } else {
                    int maxHeight = maxHeap.peek()[2];

                    if (res.size() == 0 || res.get(res.size() - 1).get(1) != maxHeight) {
                        List<Integer> tmp = new ArrayList<>();
                        tmp.add(a);
                        tmp.add(maxHeight);
                        res.add(tmp);
                    }
                }
            }

            return res;
        }
    }



}
