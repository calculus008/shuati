package leetcode;

import java.util.*;
import java.util.function.Supplier;

/**
 * Created by yuank on 10/17/18.
 */
public class LE_480_Sliding_Window_Median {
    /**
         Median is the middle value in an ordered integer list. If the size of the list is even,
         there is no middle value. So the median is the mean of the two middle value.

         Examples:
         [2,3,4] , the median is 3

         [2,3], the median is (2 + 3) / 2 = 2.5

         Given an array nums, there is a sliding window of size k which is moving from the
         very left of the array to the very right. You can only see the k numbers in the window.
         Each time the sliding window moves right by one position. Your job is to output the
         median array for each window in the original array.

         For example,
         Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

         Window position                Median
         ---------------               -----
         [1  3  -1] -3  5  3  6  7       1
         1 [3  -1  -3] 5  3  6  7       -1
         1  3 [-1  -3  5] 3  6  7       -1
         1  3  -1 [-3  5  3] 6  7       3
         1  3  -1  -3 [5  3  6] 7       5
         1  3  -1  -3  5 [3  6  7]      6

         Therefore, return the median sliding window as [1,-1,-1,3,5,6].

         Note:
         You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.

         Hard
     */

    /**
     * !!!
     * http://zxi.mytechroad.com/blog/difficulty/hard/leetcode-480-sliding-window-median/
     */

    /**
     * Solution 1
     * Similar to LI_360_Sliding_Window_Median
     * 1.return type is int[]
     * 2.test cases show that we need to handle int value overflow
     *
     * Time  : O(n ^ 2 * logk)
     *         O(n * (n + logk)) ??
     *
     * Space : O(n)
     */
    public double[] medianSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) return new double[]{};

        double[] res = new double[nums.length - k + 1];

        PriorityQueue<Long> large = new PriorityQueue<>();//min heap

        /**
         * !!!
         * For Long, can't use lambda "(a,b) -> b - a", guessing the comparator requires comparing
         * expression return int. Better use "Collections.reverseOrder()"
         */
        PriorityQueue<Long> small = new PriorityQueue<>(1000, Collections.reverseOrder());//max heap

        int l = 0; //index of the left most element in window
        int j = 0; //index of result array
        for (int i = 0; i < nums.length; i++) {//index of the right most element in window
            if (i >= k) {//remove left element
                if (nums[l] < large.peek()) {//decide which pq nums[l] is in
                    small.remove((long)nums[l]);//PriorityQueue remove is O(n)
                } else {
                    large.remove((long)nums[l]);
                }
                l++;
            }

            //maintain large and small heaps
            large.offer((long)nums[i]);
            small.offer(large.poll());
            if (small.size() > large.size()) {
                large.offer(small.poll());
            }

            //get median
            if (i - k + 1 >= 0) {
                res[j] = k % 2 == 0 ? (small.peek() + large.peek()) / 2.0 : large.peek();
                j++;
            }
        }

        return res;
    }

    /**
     * Solution 2
     *
     * Use two TreeSet and save index.
     *
     * Time : O(nlogk)
     */
    public double[] medianSlidingWindow2(int[] nums, int k) {
        Comparator<Integer> comparator = (a, b) -> nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b;
        TreeSet<Integer> left = new TreeSet<>(comparator.reversed());
        TreeSet<Integer> right = new TreeSet<>(comparator);

        Supplier<Double> median = (k % 2 == 0) ?
                () -> ((double) nums[left.first()] + nums[right.first()]) / 2 :
                () -> (double) nums[right.first()];

        // balance lefts size and rights size (if not equal then right will be larger by one)
        Runnable balance = () -> { while (left.size() > right.size()) right.add(left.pollFirst()); };

        double[] result = new double[nums.length - k + 1];

        //fill the first window
        for (int i = 0; i < k; i++) {
            left.add(i);
        }

        balance.run();
        result[0] = median.get();

        for (int i = k, r = 1; i < nums.length; i++, r++) {
            // remove tail of window from either left or right
            if(!left.remove(i - k)) right.remove(i - k);

            // add next num, this will always increase left size
            right.add(i);
            left.add(right.pollFirst());

            // rebalance left and right, then get median from them
            balance.run();
            result[r] = median.get();
        }

        return result;
    }

    /**
     * Solution 3
     * Use 2 TreeSet, adapt to similar flow as in Solution 1
     *
     * Time : O(nlogk)
     * Space : O(n)
     *
     * Use two TreeSet and save index.
     *
     * Best Solution
     */
    public double[] medianSlidingWindow3(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) return new double[]{};

        double[] res = new double[nums.length - k + 1];

        /**
         * !!!
         * This is the key trick for using TreeSet. Instead of saving value, we save INDEX(!!!) for nums
         * in TreeSet, since index is unique, we can save them in a set.
         *
         * This Comparator tells TreeSet to sort based on the values in nums by using indexed saved in
         * the set.
         *
         * To break ties in our Tree Set comparator we compare the index.
         *
         * "comparator.reversed()"
         */
        Comparator<Integer> comparator = (a, b) -> nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b;
        TreeSet<Integer> small = new TreeSet<>(comparator.reversed());
        TreeSet<Integer> large = new TreeSet<>(comparator);

        int l = 0;
        int j = 0;

        /**
         * Overall action for each window to get median
         * 1.Remove left
         * 2.Add right
         * 3.Balance between large and small
         * 4.Calculate median
         */
        for (int i = 0; i < nums.length; i++) {
            /**
             * When arrive at index k, the window is full, we need to remove element from left side
             */
            if (i >= k) {
                /**
                 * TreeSet.remove() returns false if the value is not in current set!
                 * It takes O(logk)
                 */
                if (!small.remove(l)) {
                    large.remove(l);
                }
                l++;
            }


            large.add(i);
            small.add(large.pollFirst());
            if (small.size() > large.size()) {//can also use "while"
                large.add(small.pollFirst());
            }

            /**
             * Execute one loop before the logic of removing element at the beginning of the loop
             */
            if (i >= k - 1) {
                /**
                 * Use long to avoid overflow
                 *
                 * TreeSet.first()
                 */
                res[j] = (k % 2 == 0 ? ((long)nums[small.first()] + (long)nums[large.first()]) / 2.0 : (long)nums[large.first()]);
                j++;
            }
        }

        return res;
    }

    /**
     * Insertion Sort Solution
     *
     * Time  : O((n - k + 1)logn)
     *         k size window moves n - k + 1 times, each time cost O(n) with insertion sort.
     * Space : O(k)
     *
     * Author: Huahua
     * Running time:
     * 60 ms
     *
     * While moving the window, maintain the elements in window in sorted order.
     * Use insertion sort when add and delete elements from window. Main cost is
     * shifting elements each time add/delete (O(k))
     *
     **/
    class Solution4 {
        public double[] medianSlidingWindow(int[] nums, int k) {
            if (k == 0) return new double[0];
            double[] ans = new double[nums.length - k + 1];
            int[] window = new int[k];

            for (int i = 0; i < k; ++i) {
                window[i] = nums[i];
            }

            Arrays.sort(window);//!!!

            for (int i = k; i <= nums.length; ++i) {
                //median for current window
                ans[i - k] = ((double) window[k / 2] + window[(k - 1) / 2]) / 2;

                if (i == nums.length) {
                    break;
                }

                remove(window, nums[i - k]);
                insert(window, nums[i]);
            }
            return ans;
        }

        // Insert val into window, window[k - 1] is empty before inseration
        private void insert(int[] window, int val) {
            int i = 0;
            while (i < window.length - 1 && val > window[i]) ++i;
            int j = window.length - 1;
            while (j > i) window[j] = window[--j];
            window[j] = val;
        }

        // Remove val from window and shrink it.
        private void remove(int[] window, int val) {
            int i = 0;
            while (i < window.length && val != window[i]) ++i;
            while (i < window.length - 1) window[i] = window[++i];
        }
    }


    class Solution_Practice {
        public double[] medianSlidingWindow(int[] nums, int k) {
            /**
             * Boundary check
             */
            if (null == nums || nums.length == 0 || k == 0) {
                return new double[]{};
            }

            /**
             * !!!
             * Must remember how to write comparator
             * "comparator.reversed()"
             */
            Comparator<Integer> comparator = (a, b) -> nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b;
            TreeSet<Integer> large = new TreeSet<>(comparator);
            TreeSet<Integer> small = new TreeSet<>(comparator.reversed());

            int n = nums.length;
            double[] res = new double[n - k + 1];

            /**
             * l : left side of window
             * r : right side of window
             * idx : index in res[]
             */
            int l = 0;
            int idx = 0;
            for (int r = 0; r < n; r++) {
                /**
                 * !!!
                 * only start removing left most element in window when right side hit index value k
                 * (window 0 ~ k - 1 has k elements, index k is its k + 1 th element, must do remove)
                 */
                if (r >= k) {
                    if (!large.remove(l)) {
                        small.remove(l);
                    }
                    l++;
                }

                large.add(r);
                small.add(large.pollFirst());
                while (small.size() > large.size()) {
                    large.add(small.pollFirst());
                }

                /**!!!
                 * Don't forget this if condition
                 * res starts to have value when r hits index k - 1 (first full window with k elements)
                 */
                if (r >= k - 1) {
                    /**
                     * !!!
                     * 1.Don't forget, what stores in large and small are INDEX!!!
                     *   So after retrieve it using "first()", the value is in "nums[large.first()]" and "nums[small.first()]"
                     * 2.Must consider int overflow, deal with it by converting to long
                     */
                    res[idx] = (k % 2 == 1) ? (long) nums[large.first()] / 1.0 : (((long)nums[large.first()] + (long)nums[small.first()]) / 2.0);
                    idx++;
                }
            }

            return res;
        }
    }
}
