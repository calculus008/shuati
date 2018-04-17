package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/22/18.
 */
public class LE_164_Maxium_Gap {
    /*
        Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

        Try to solve it in linear time/space.

        Return 0 if the array contains less than 2 elements.

        You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.

     */

    //Bucket Sort
    //Time and Space : O(n)
    //https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space
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

    /*
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
        Arrays.fill(bucketMin, Integer.MAX_VALUE);
        Arrays.fill(bucketMax, Integer.MIN_VALUE);

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
}
