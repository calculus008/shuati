package leetcode;

/**
 * Created by yuank on 3/19/18.
 */
public class LE_152_Maximum_Product_Subarray {
    /**
        Find the contiguous subarray within an array (containing at least one number)
        which has the largest product.

        For example, given the array [2,3,-2,4],
        the contiguous subarray [2,3] has the largest product = 6.
     */

    /**
     * Time : O(n)
     * Space : O(1)
     * https://leetcode.com/problems/maximum-product-subarray/discuss/48252/Sharing-my-solution:-O(1)-space-O(n)-running-time
     *
     * Since there could be negative numbers in array, we need to keep track and max and min
     * products while traversing the array. If min value in negative, when it's multiplied by
     * a negative number, it will turn into positive and can possibly become max.
     */
    public int maxProduct1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int max = nums[0], min = nums[0], res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = max;
            max = Math.max(Math.max(max * nums[i], min * nums[i]), nums[i]);
            min = Math.min(Math.min(temp * nums[i], min * nums[i]), nums[i]);
            res = Math.max(res, max);
        }

        return res;
    }

    public int maxProduct2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int minPre = nums[0];
        int maxPre = nums[0];
        int max = nums[0];
        int min = nums[0];
        int res = nums[0];

        for (int i = 1; i < nums.length; i ++) {
            /**
             * !!! "nums[i]"
             */
            max = Math.max(nums[i], Math.max(maxPre * nums[i], minPre * nums[i]));
            min = Math.min(nums[i], Math.min(maxPre * nums[i], minPre * nums[i]));
            res = Math.max(res, max);
            maxPre = max;
            minPre = min;
        }
        return res;
    }
}
