package leetcode;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;
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
     * Solution 1
     * Similar to LI_360_Sliding_Window_Median
     * 1.return type is int[]
     * 2.test cases show that we need to handle int value overflow
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

        int l = 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i >= k) {//remove left element
                if (nums[l] < large.peek()) {
                    small.remove((long)nums[l]);
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
     * 2 TreeSet
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
        for (int i = 0; i < k; i++) left.add(i);

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
     * Time : O(nlogk)
     * Space : O(n)
     *
     * Best Solution
     */
    public double[] medianSlidingWindow3(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) return new double[]{};

        double[] res = new double[nums.length - k + 1];

        /**
         * !!!
         * This is the key trick for using TreeSet. Instead of saving value, we save index for nums
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
}
