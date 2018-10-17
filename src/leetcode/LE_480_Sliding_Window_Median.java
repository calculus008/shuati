package leetcode;

import java.util.Collections;
import java.util.PriorityQueue;

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
    public double[] medianSlidingWindow(int[] nums, int k) {
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
}
