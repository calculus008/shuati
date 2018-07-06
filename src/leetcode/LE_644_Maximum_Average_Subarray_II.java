package leetcode;

/**
 * Created by yuank on 7/5/18.
 */
public class LE_644_Maximum_Average_Subarray_II {
    /**
         Given an array consisting of n integers, find the contiguous subarray whose length is
         greater than or equal to k that has the maximum average value.
         And you need to output the maximum average value.

         Example 1:
         Input: [1,12,-5,-6,50,3], k = 4
         Output: 12.75
         Explanation:
         when length is 5, maximum average value is 10.8,
         when length is 6, maximum average value is 9.16667.
         Thus return 12.75.
         Note:
         1 <= k <= n <= 10,000.
         Elements of the given array will be in range [-10,000, 10,000].
         The answer with the calculation error less than 10-5 will be accepted.

         Hard
     */

    /**
     * Time : O(nlogm), m = max - min
     *
     *
     */
    public double maxAverage(int[] nums, int k) {
        if(nums == null || k > nums.length) {
            return 0;
        }

        double start = Double.MAX_VALUE;
        double end = Double.MIN_VALUE;

        /**
         *  这里是计算average,所以我们用一次遍历找出数组中最大和最小的值，那average必定在他们中间，
         *  于是我们便可以在max和min中间使用binary search
         */
        for(int i = 0; i < nums.length; i++) {
            start = Math.min(start, (double)nums[i]);
            end = Math.max(end, (double)nums[i]);
        }

        double eps = 1e-6;

        double avg = 0;

        while(start + eps < end) {
            avg = start + (end - start) / 2;
            if(checkAvg(nums, k, avg)) {
                start = avg;
            } else {
                end = avg;
            }
        }

        return start;
    }

    private boolean checkAvg(int[] nums, int k, double avg) {
        double sum = 0;
        double prevSum = 0;
        double prevMin = 0;

        for(int i = 0; i < nums.length; i++) {
            sum += (double)nums[i] - avg;

            /**
             * For the case that subarray starts from the first element nums[0]
             */
            if(i >= k - 1 && sum >= 0) {
                return true;
            }

            /**
                 找一段长度 >= k 的子数组使它的平均值满足条件。
                 Sum 是 0 到 i 所有元素之和，而 prevSum 是 0 到 i - k 的所有元素之和，这样可以保证用 Sum - PreSum 的数组长度比 k 长
                 prevMin 来记录 prevSum 的最小值，通过 sum 减去 prevMin 来获得子数组可能的最大值

                 Or

                 (nums[i]+nums[i+1]+...+nums[j])/(j-i+1)>x
                 =>nums[i]+nums[i+1]+...+nums[j]>x*(j-i+1)
                 =>(nums[i]-x)+(nums[i+1]-x)+...+(nums[j]-x)>0
             **/
            if(i >= k) {
                prevSum += (double)nums[i - k] - avg;
                prevMin = Math.min(prevMin, prevSum);
                if(sum - prevMin >= 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
