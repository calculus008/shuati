package leetcode;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LE_632_Smallest_Range {
    /**
     * You have k lists of sorted integers in ascending order.
     * Find the smallest range that includes at least one number
     * from each of the k lists.
     *
     * We define the range [a,b] is smaller than range
     * [c,d] if b-a < d-c or a < c if b-a == d-c.
     *
     * Example 1:
     * Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
     * Output: [20,24]
     * Explanation:
     * List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
     * List 2: [0, 9, 12, 20], 20 is in range [20,24].
     * List 3: [5, 18, 22, 30], 22 is in range [20,24].
     *
     * Note:
     * The given list may contain duplicates, so ascending order means >= here.
     * 1 <= k <= 3500
     * -105 <= value of elements <= 105.
     * For Java users, please note that the input type has been changed to
     * List<List<Integer>>. And after you reset the code template,
     * you'll see this point.
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/smallest-range/solution/
     *
     * Brutal Force is O(n ^ 3)
     *
     * With Heap Solution:
     * Time : O(nlogm)
     * Space : O(m) (heap size)
     *
     * It is a variation from LE_23_Merge_k_Sorted_Lists
     *
     * Example :
     *
     * [
     *  [4,10,15,24,26],
     *  [0,9,12,20],
     *  [5,18,22,30]
     * ]
     *
     * Heap : {0, 4, 5}
     * max  : 5
     *
     * e = 0,
     * start = 0, end = 5, range = 5
     * max = 9
     * add 9
     * Heap : {4, 5, 9}
     *
     * e = 4,
     * start = 0, end = 5, range = 5 (no change)
     * max = 10
     * add 10
     * Heap : {5, 9, 10}
     *
     * e = 5
     * start = 0, end = 5, range = 5 (no change)
     * max = 18
     * add 18
     * Heap : {9, 10, 18}
     *
     * e = 9
     * start = 0, end = 5, range = 5 (no change)
     * max = 18
     * add 12
     * Heap : {10 ,12, 18}
     *
     * e = 10
     * start = 0, end = 5, range = 5 (no change)
     * max = 18
     * add 15
     * Heap : {12, 15, 18}
     *
     * e = 12
     * start = 0, end = 5, range = 5 (no change)
     * max = 20
     * add 20
     * Heap : {15, 18, 20}
     *
     * e = 15
     * start = 0, end = 5, range = 5 (no change)
     * max = 24
     * add 24
     * Heap : {18, 20, 24}
     *
     * e = 18
     * start = 0, end = 5, range = 5 (no change)
     * max = 24
     * add 22
     * Heap : {20, 22, 24}
     *
     * e = 20
     * start = 20, end = 24, range = 4
     *
     * Not add number to heap
     *
     * Heap : {22, 24}
     *
     * Heap size < 3, whil loop stops.
     *
     * Final answer :
     * start = 20, end = 24, range = 4
     */
    class Solution1 {
        public int[] smallestRange(List<List<Integer>> nums) {
            PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

            int n = nums.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int[] e = new int[]{nums.get(i).get(0), i, 0};
                heap.offer(e);
                max = Math.max(max, e[0]);
            }

            int range = Integer.MAX_VALUE;
            int start = -1;
            int end = -1;
            while (heap.size() == nums.size() && range != 0) {
                int[] min = heap.poll();
                if (max - min[0] < range) {
                    start = min[0];
                    end = max;
                    range = end - start;
                }

                if (min[2] + 1 < nums.get(min[1]).size()) {
                    int m = nums.get(min[1]).get(min[2] + 1);
                    max = Math.max(max, m);
                    int[] newEle = new int[]{m, min[1], min[2] + 1};
                    heap.offer(newEle);
                }
            }

            return new int[]{start, end};
        }
    }

    class Solution2 {
        class Element {
            int val;
            int row;
            int col;

            public Element(int v, int r, int i) {
                val = v;
                row = r;
                col = i;
            }
        }

        public int[] smallestRange(List<List<Integer>> nums) {
            PriorityQueue<Element> heap = new PriorityQueue<>((a, b) -> a.val - b.val);

            int n = nums.size();

            int max = Integer.MIN_VALUE;
            int start = -1;
            int end = -1;

            /**
             * put all starting element in each list into heap
             * and get the largest number among them.
             */
            for (int j = 0; j < n; j++) {
                int a = nums.get(j).get(0);
                heap.offer(new Element(a, j, 0));
                max = Math.max(max, a);
            }

            /**
             * Initial range : maxStartElem - minStartElem, so it covers at least one element in each list.
             * Same idea of K-way merge. For max and min, heap can track the min of all elements.
             * Each time when we add element into heap, it is a potential contender for max (since it is
             * bigger or equal to the min value that is just popped). Therefore, keep updating max and
             * pop up min, tracking what is the current min range. When heap size is smaller than n, it means
             * we no longer have elements from all lists, so it is time to stop.
             *
             */
            int range = Integer.MAX_VALUE;
            while (heap.size() == n) {
                Element e = heap.poll();
                int value = e.val;

                if (max - value < range) {
                    start = value;
                    end = max;
                    range = end - start;
                }

                int r = e.row;
                int c = e.col;
                if (c + 1 < nums.get(r).size()) {
                    int newVal = nums.get(r).get(c + 1);
                    max = Math.max(max, newVal);
                    heap.offer(new Element(newVal, r, c + 1));
                }
            }

            return new int[]{start, end};

        }
    }
}