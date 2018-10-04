package lintcode;

import java.util.ArrayList;

/**
 * Created by yuank on 10/2/18.
 */
public class LI_42_Maximum_Subarray_II {
    /**
         Given an array of integers, find two non-overlapping
         subarrays which have the largest sum.
         The number in each subarray should be contiguous.
         Return the largest sum.

         Example
         For given [1, 3, -1, 2, -1, 2], the two subarrays are [1, 3] and [2, -1, 2] or
         [1, 3, -1, 2] and [2], they both have the largest sum 7.

         Challenge
         Can you do it in time complexity O(n) ?

         Notice
         The subarray should contain at least one number

         Medium
     */

    /**
     * 这个题的思路是，因为 两个subarray 一定不重叠,所以必定存在一条分割线分开这两个 subarrays
     * 所以 最后的部分里：
     *
     *   max = Integer.MIN_VALUE;
     *   for(int i = 0; i < size - 1; i++){
     *     max = Math.max(max, left[i] + right[i + 1]);
     *   }
     *   return max;
     *
     * 这里是在枚举 这条分割线的位置
     * 然后 left[] 和 right[] 里分别存的是，
     * 某个位置往左的 maximum subarray 和往右的 maximum subarray
     */
    public int maxTwoSubArrays(ArrayList<Integer> nums) {
        int size = nums.size();
        int[] left = new int[size];
        int[] right = new int[size];

        int sum = 0;
        int minSum = 0;
        int max = Integer.MIN_VALUE;

        //-------->
        for(int i = 0; i < size; i++){
            sum += nums.get(i);
            max = Math.max(max, sum - minSum);
            minSum = Math.min(minSum, sum);
            left[i] = max;
        }

        sum = 0;
        minSum = 0;
        max = Integer.MIN_VALUE;

        //<---------
        for(int i = size - 1; i >= 0; i--){
            sum += nums.get(i);
            max = Math.max(max, sum - minSum);
            minSum = Math.min(minSum, sum);
            right[i] = max;
        }

        max = Integer.MIN_VALUE;

        for(int i = 0; i < size - 1; i++){
            max = Math.max(max, left[i] + right[i + 1]);
        }

        return max;
    }
}
