package leetcode;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_209_Minimum_Size_Subarray_Sum {
    /*
        Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray of which the sum ≥ s.
        If there isn't one, return 0 instead.

        For example, given the array [2,3,1,2,4,3] and s = 7,
        the subarray [4,3] has the minimal length under the problem constraint.
     */

    //Sliding Window, Time : O(n), Space : O(1)
    public int minSubArrayLen(int s, int[] nums) {
        int res = Integer.MAX_VALUE;
        int sum = 0, left = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while ( left < nums.length && sum >= s) {
                res = Math.min(res, i - left + 1);
                sum -= nums[left++];
            }
        }

        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
