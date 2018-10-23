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
     * Solution also works for LE_643_Maximum_Average_Subarray_I
     */
    public double maxAverage1(int[] nums, int k) {
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
            start = Math.min(start, (double)nums[i]); //double 转换
            end = Math.max(end, (double)nums[i]); //double 转换
        }

        double eps = 1e-6; //double

        double avg = 0; //double

        /**
         * 对于average,在可能的范围内(数组中的最小和再大值之间), 二分查找。
         * 对于每一个可能的值，用checkAvg(),看以给定的平均值avg，是否存在
         * subarray,其平均值大于avg, 如果yes, 就是说平均值还可以更大，于是
         * 往右移动区间，如果no, 就是说当前的avg太大了，得缩小，于是往左移动
         * 区间。
         */
        while(start + eps < end) {//O(log(max - min))
            avg = start + (end - start) / 2;
            if(checkAvg(nums, k, avg)) {
                start = avg;
            } else {
                end = avg;
            }
        }

        return start;
    }

    private boolean checkAvg(int[] nums, int k, double avg) {//O(n)
        double sum = 0;  //double
        double prevSum = 0; //double
        double prevMin = 0; //double

        /**
         * 存在一个长度大于等于k的subarray, 其平均值大于等于avg，
         * 使用presum的技巧。
         */
        for(int i = 0; i < nums.length; i++) {
            sum += (double)nums[i] - avg; //double 转换

            /**
             * 这里是检查subarray从下标0开始的情况。
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
                prevSum += (double)nums[i - k] - avg; //double 转换
                prevMin = Math.min(prevMin, prevSum);
                if(sum - prevMin >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Solution 2
     * Binary Search
     * Time : O(nlog(max - min))
     */
    public double maxAverage(int[] nums, int k) {
        if(nums == null || k > nums.length) {
            return 0;
        }

        double start = Double.MAX_VALUE;
        double end = Double.MIN_VALUE;

        for(int i = 0; i < nums.length; i++) {
            start = Math.min(start, (double)nums[i]);
            end = Math.max(end, (double)nums[i]);
        }

        double eps = 1e-6;

        double avg = 0;

        while(start + eps < end) {
            avg = start + (end - start) / 2;
            if(checkAvg1(nums, k, avg)) {
                start = avg;
            } else {
                end = avg;
            }
        }

        return start;
    }

    private boolean checkAvg1(int[] nums, int k, double avg) {
        double sum = 0;
        double prevSum = 0;
        double prevMin = 0;

        for(int i = 0; i < nums.length; i++) {
            sum += (double)nums[i] - avg;

            if(i >= k - 1 && sum >= 0) {
                return true;
            }

            /**
             找一段长度 >= k 的子数组使它的平均值满足条件。
             Sum 是 0 到 i 所有元素之和，而 prevSum 是 0 到 i - k 的所有元素之和，
             这样可以保证用 Sum - PreSum 的数组长度比 k 长
             prevMin 来记录 prevSum 的最小值，通过 sum 减去 prevMin 来获得子数组可能的最大值
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

    /**
     * Solution 3, same algorithm as Solution 2
     */
    public double maxAverage2(int[] nums, int k) {
        // Write your code here
        double l = -1e12;
        double r = 1e12;
        double eps = 1e-6;

        while (l + eps < r) {
            double mid = l + (r - l) / 2;

            if (check(nums, mid, k)) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return l;
    }

    boolean check(int nums[], double avg, int k) {
        double[] sum = new double[nums.length + 1];
        double[] min_pre = new double[nums.length + 1];

        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + (nums[i - 1] - avg);
            min_pre[i] = Math.min(min_pre[i - 1], sum[i]);

            if (i >= k && sum[i] - min_pre[i - k] >= 0) {
                return true;
            }
        }
        return false;
    }
}
