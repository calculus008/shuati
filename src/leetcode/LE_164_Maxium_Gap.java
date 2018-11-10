package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/22/18.
 */
public class LE_164_Maxium_Gap {
    /**
        Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

        Try to solve it in linear time/space.

        Return 0 if the array contains less than 2 elements.

        You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.

     */

    /**
        Bucket Sort
        Time and Space : O(n)
        https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space

         Suppose there are N elements in the array, the min value is min and the max value is max.
         Then the maximum gap will be NO SMALLER than ceiling[(max - min ) / (N - 1)].

         Let gap = ceiling[(max - min ) / (N - 1)]. We divide all numbers in the array into n-1 buckets,
         where k-th bucket contains all numbers in [min + (k-1)gap, min + k*gap). Since there are n-2 numbers
         that are not equal min or max and there are n-1 buckets, at least one of the buckets are empty.
         We only need to store the largest number and the smallest number in each bucket.

         After we put all the numbers into the buckets. We can scan the buckets sequentially and get the max gap.

         "We put n-2 elements in n-1 buckets." That's the key. At least one bucket is empty, so the max gap can't come from
         a single bucket. It can only come from two adjacent buckets, or one bucket and (max or min).

     **/
    public int maximumGap1(int[] nums) {
        if (nums.length < 2) return 0;

        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        if (min == max) return 0;

        //!!! notice the cast from double to int
        int bucketSize = (int)Math.ceil((double)(max - min) / (nums.length - 1));

        int[] bucketMin = new int[nums.length - 1];
        int[] bucketMax = new int[nums.length - 1];
        Arrays.fill(bucketMin, Integer.MAX_VALUE);
        Arrays.fill(bucketMax, Integer.MIN_VALUE);

        for (int num : nums) {
            if (num == min || num == max) continue;
            int bucketIdx = (num - min) / bucketSize;
            bucketMin[bucketIdx] = Math.min(bucketMin[bucketIdx], num);
            bucketMax[bucketIdx] = Math.max(bucketMax[bucketIdx], num);
        }

        int maxGap = 0;
        int pre = min;
        for (int i = 0; i < nums.length - 1; i++) {
            if (bucketMin[i] == Integer.MAX_VALUE && bucketMax[i] == Integer.MIN_VALUE) continue; //Empty Bucket
            maxGap = Math.max(maxGap, bucketMin[i] - pre);
            pre = bucketMax[i];
        }

        return Math.max(maxGap, max - pre);
    }

    /**
        1. if min==max, we can return 0 directly.
        2. the length of bucketMin and bucketMax is n rather than n-1. So max can be put in bucket.
        3. to check if bucket is empty, check if(bucketMin[i]!=Integer.MAX_VALUE) is ok
        4. do not need maxGap, gap is enough.
     */
    public int maximumGap2(int[] nums) {
        if(nums==null || nums.length<2)
            return 0;

        int min=nums[0];
        int max=nums[0];
        for(int n: nums){
            min=Math.min(min, n);
            max=Math.max(max, n);
        }
        if(min==max)
            return 0;

        int n=nums.length;

        int gap = (int)Math.ceil((double)(max-min)/(n-1));
        int bucketMin[] = new int[n];
        int bucketMax[] = new int[n];
        Arrays.fill(bucketMin, -1);
        Arrays.fill(bucketMax, -1);

        for(int num: nums){
            int i=(num-min)/gap;
            bucketMin[i] = Math.min(bucketMin[i], num);
            bucketMax[i] = Math.max(bucketMax[i], num);
        }


        for(int i=0;i<bucketMin.length;++i){
            if(bucketMin[i]!=Integer.MAX_VALUE){
                gap = Math.max(gap, bucketMin[i]-min);
                min = bucketMax[i];
            }
        }

        return gap;
    }

    /**
     * This solution passes LeetCode, LintCode has test case that there's Integer.MAX_VALUE and Integer.MIN_VALUE
     * in nums, need to do extra processing.
     */
    public int maximumGap3(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int min = nums[0], max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        if (min == max) {
            return 0;
        }

        int n = nums.length;
        int[] bucketMin = new int[n];
        int[] bucketMax = new int[n];
        Arrays.fill(bucketMin, Integer.MAX_VALUE);
        Arrays.fill(bucketMax, Integer.MIN_VALUE);

        int gap = (int)Math.ceil((double)(max - min) / (n - 1));

        for (int num : nums) {
            int bucketIdx = ((num - min) / gap);
            bucketMin[bucketIdx] = Math.min(num, bucketMin[bucketIdx]);
            bucketMax[bucketIdx] = Math.max(num, bucketMax[bucketIdx]);
        }

        int res = 0;
        for (int i = 0; i < bucketMin.length; i++) {
            if (bucketMin[i] != Integer.MAX_VALUE) {
                res = Math.max(res, bucketMin[i] - min);//!!!bucketMin
                min = bucketMax[i];//!!!bucketMax
            }
        }

        return res;
    }

    /**
     * Use long to fix the issue if there's Integer.MAX_VALUE in nums[]
     */
    public static int maximumGap4(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int min = nums[0], max = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        //!!!
        if (min == max) {
            return 0;
        }

        int n = nums.length;

        /**
         * !!! Must be size of n
         *
         * For case [0, 10], min = 0, max = 10, gap = (10 - 0) / (2 - 1) = 10,
         * Therefore when calculating bucket index for 10:
         * (10 - 0) / 10 = 1
         *
         * If bucketMin is of size n - 1 (1), then index 1 is out of index boundary
         * **/
        long[] bucketMin = new long[n];
        long[] bucketMax = new long[n];
        Arrays.fill(bucketMin, Long.MAX_VALUE);
        Arrays.fill(bucketMax, Long.MIN_VALUE);

        int gap = (int)Math.ceil((double)(max - min) / (n - 1));

        for (int num : nums) {
            int bucketIdx = ((num - min) / gap);

            System.out.println(num + " belongs to bucket "  + bucketIdx);

            bucketMin[bucketIdx] = Math.min(num, bucketMin[bucketIdx]);
            bucketMax[bucketIdx] = Math.max(num, bucketMax[bucketIdx]);
        }

        long res = 0;

        System.out.println("gap = " + gap);
        System.out.println(Arrays.toString(bucketMin));
        System.out.println(Arrays.toString(bucketMax));

        /**
         * 使用int类型pre来记住前一个bucket的下标(有可能有空bucket),
         * 避免记住bucket中的值，因为值是long型的，会有转换的麻烦。
         */
        int pre = 0;
        for (int i = 0; i < bucketMin.length; i++) {
            if (bucketMin[i] != Long.MAX_VALUE) {
                System.out.println("res=" + res + ", " + bucketMin[i] + " - " + bucketMax[pre]);

                /**
                 * !!!当前bucket的MIN减去上一个bucket的MAX
                 */
                res = Math.max(res, bucketMin[i] - bucketMax[pre]);//!!!bucketMin
                pre = i;//!!!bucketMax
            }
        }

        return (int)res;
    }

    public static void main(String [] args) {
        int[] input = new int[]{1, 9, 2, 5};
        int[] input1 = new int[]{0, Integer.MAX_VALUE};

        maximumGap4(input1);
    }
}
