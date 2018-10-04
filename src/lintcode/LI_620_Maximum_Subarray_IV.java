package lintcode;

/**
 * Created by yuank on 10/2/18.
 */
public class LI_620_Maximum_Subarray_IV {
    /**
         Given an integer arrays, find a contiguous subarray which has the
         largest sum and length should be greater or equal to given length k.
         Return the largest sum, return 0 if there are fewer than k elements in the array.

         Example
         Given the array [-2,2,-3,4,-1,2,1,-5,3] and k = 5,
         the contiguous subarray [2,-3,4,-1,2,1] has the largest sum = 5.

         Notice
         Ensure that the result is an integer type.

         Medium
     */

    /**
     * 提供一个O(n)时间，O(1)空间的解法，在617 Maximum Average Subarray II中也用的上
     * 其实就是并不需要记录所有的presum，只需要在第一个pointer遍历到K之后，第二个pointer再从头开始遍历，
     * 第二个pointer每次遍历一个新的数字的时候就更新最小的presum, 这样第一个pointer和第二个pointer之间间隔K个元素，
     * 可以保证第一个pointer的sum - 第二个pointer前面的minSum，一定满足条件
     */
    public int maxSubarray4(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return 0;
        }

        int sum = 0;
        int minSum = 0;
        int max = Integer.MIN_VALUE;
        int preSum = 0;//2nd pointer starts from begining

        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        for (int i = k; i < nums.length; i++) {
            max = Math.max(max, sum - minSum);
            sum += nums[i];
            preSum += nums[i - k];
            minSum = Math.min(minSum, preSum);
        }

        //!!! one more to deal with after loop ends
        return Math.max(max, sum - minSum);
    }
}
