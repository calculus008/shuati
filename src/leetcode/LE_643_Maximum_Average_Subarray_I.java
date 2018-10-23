package leetcode;

/**
 * Created by yuank on 7/5/18.
 */
public class LE_643_Maximum_Average_Subarray_I {
    /**
         Given an array consisting of n integers, find the contiguous
         subarray of given length k that has the maximum average value.
         And you need to output the maximum average value.

         Example 1:
         Input: [1,12,-5,-6,50,3], k = 4
         Output: 12.75
         Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
         Note:
         1 <= k <= n <= 30,000.
         Elements of the given array will be in the range [-10,000, 10,000].

         Easy
     */

    /**
     *   Solution 1
     *   Sliding Window
     *   Time : O(n)
     *
     *   0  1  2  3    4 5
     *   [1,12,-5,-6,  50,3]
     *
     *   sum += nums[4] - nums[0]
     **/
    public double findMaxAverage(int[] nums, int k) {
        /**
         * !!!Must use long here to prevent overflow when we do sum.
         */
        long sum = 0; //!!! long
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        long max = sum;

        /**
         * This actually is a sliding window, kick out the first one in last window
         * and add the next one outside the last window.
         */
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            max = Math.max(max, sum);
        }

        /**
         * !!! must first divide by 1.0 to convert it to double, then divide by k
         * Can't reverse the order
         */
        return max / 1.0 / k; //!!! 1.0
    }

    /**
     https://www.jiuzhang.com/solution/maximum-average-subarray/#tag-other

     Advanced solution:
     Another solution is not try to calculate the max value but try to find a value that close enough
     to the max average value. When it is close enough, could consider that value is the max average value.
     Time complexity will be nlog(max - min).

     1.Average bound
     Average value of any number of elements in the array must be in this bound:

     Minimum average >= min element in the array
     Maximum average <= max element in the array

     2.Binary search
     To find the value, do binary search against the INTERVAL of the bound.

     Set the bar to be mid value of the max and min value
     if we could find a subarray whose length is >=k and average >= bar this means the bar is too low,
     we should raise the bar, i.e. set min bound = bar.

     if all the subarrays whose length is >= k but average < bar this means the bar is too high,
     we should lower the bar, i.e. set max bound = bar.

     when the differences between the max and min bound is close enough, this means the bar is close enough
     to the max avarage value we could found, then return the max value (or min value or the mid value doesn't matter).

     3.Evaluation
     Key problem is how to find the subarray whose length is >= k and average >= bar?

     Use pre-sum array
     But here we don't aggregate (pre-sum) the array elements, we aggregate the differences
     between each element and the bar.

     If a number in the source array is bigger than the bar, it means it will pull up the subarray average to the bar,
     if a number in the source array is smaller than the bar, it means it will pull down the subarray average to the bar.

     With the aggragation of the differences, we don't need to really calculate the sum of elements and divide k,
     we only need to see the trend of the average comparing to the bar.

     Iterate through the pre-sum array
     When i >=k i.e. here the subarray from first element to ith element length is >= k.

     If the ith pre-sum is >= 0, it means the first ith elements average is bigger than the bar,
     so we should raise the bar (return true).

     If the ith pre-sum is lower than the bar and no other pre-sum is higher than the bar,
     we will try to find the minimum value from 1th aggregation to (i - k)th. This minimum value means
     the largest distance between the bar in first (i - k)th aggregations (assume it is at x, i.e 1 <= x <= i - k).
     If the aggregation of ith is larger than this largest distance, it means values from x + 1 to i could fill
     that largest gap, so there must have a subarray from x + 1 to i could pull the average above the bar,
     in this case we should also raise the bar (return true).

     Here we just use the pre-sum index 0 to track the min value between 1 and i - k,
     we could use another variable to track it too.

     If we can't find any case satisfy requirements, in step (2) this mean the bar is too high, we need lower the bar.
     */
    public double maxAverage2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }

        double max = (double) Integer.MIN_VALUE;
        double min = (double) Integer.MAX_VALUE;

        int n = nums.length;

        for (int i = 0; i < n; i++) {
            max = Math.max(max, (double) nums[i]);
            min = Math.min(min, (double) nums[i]);
        }

        while (max - min > 1e-6d) {
            // set the bar
            double bar = (max + min) / 2.0d;
            if (evaluate(nums, k, bar)) {
                // raise the bar
                min = bar;
            } else {
                // lower the bar
                max = bar;
            }
        }

        return max;
    }

    private boolean evaluate(int[] nums, int k, double bar) {
        int n = nums.length;
        double[] sums = new double[n + 1];
        sums[0] = 0.0d;

        for (int i = 1; i <= n; i++) {
            // aggregate the differences between bar and element
            sums[i] = sums[i - 1] + (nums[i - 1] - bar);

            if (i >= k && sums[i] >= 0.0d) {
                // first ith elements average is above the bar
                return true;
            }

            if (i >= k) {
                // use sums[0] to track the min value
                sums[0] = Math.min(sums[0], sums[i - k]);
                if (sums[i] - sums[0] >= 0.0d) {
                    // elements could close the largest gap
                    return true;
                }
            }
        }
        return false;
    }
}
