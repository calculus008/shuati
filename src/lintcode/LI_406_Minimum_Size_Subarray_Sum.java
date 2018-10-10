package lintcode;

/**
 * Created by yuank on 10/7/18.
 */
public class LI_406_Minimum_Size_Subarray_Sum {
    /**
         Given an array of n positive integers and a positive integer s,
         find the minimal length of a subarray of which the sum ≥ s.
         If there isn't one, return -1 instead.

         Example
         Given the array [2,3,1,2,4,3] and s = 7, the subarray [4,3]
         has the minimal length under the problem constraint.

         Challenge
         If you have figured out the O(n) solution,
         try coding another solution of which the time complexity is O(n log n).

         Medium
    `
         LE_209_Minimum_Size_Subarray_Sum
     */

    /**
     * Solution 1
     * Sliding Window
     * Time  : O(n)
     * Space : O(1)
     */
    public int minimumSize1(int[] nums, int s) {
        if (nums == null || nums.length == 0) return -1;

        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0, j = 0; i < nums.length; i++) {
            sum += nums[i];
            while (j < nums.length && sum >= s) {
                min = Math.min(min, i - j + 1);
                /**
                 * !!! Wrong, 先计算再加1!!!
                 * */
                // j++;
                sum -= nums[j++];
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    /**
     * Solution 2
     * Binary Search
     * Time  : O(nlogn)
     * Space : O(1)
     */
    public int minimumSize2(int[] nums, int s) {
        if (nums == null || nums.length == 0) return -1;

        /**
         * 对size二分
         */
        int left = 1, right = nums.length;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            boolean valid = maxWindowSum(nums, mid) >= s;
            if (valid) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (maxWindowSum(nums, left) >= s) return left;
        if (maxWindowSum(nums, right) >= s) return right;
        return -1;
    }

    /**
     * 给定size, 找出长度为size的subarray的最大和
     */
    public int maxWindowSum(int[] nums, int size) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            sum += nums[i];
        }
        int max = sum;
        for (int i = size; i < nums.length; ++i) {
            sum += nums[i] - nums[i - size];
            max = Math.max(sum, max);
        }
        return max;
    }
}
