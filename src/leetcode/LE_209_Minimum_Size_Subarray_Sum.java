package leetcode;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_209_Minimum_Size_Subarray_Sum {
    /**
        Given an array of n positive integers and a positive integer s,
        find the minimal length of a contiguous subarray of which the sum ≥ s.
        If there isn't one, return 0 instead.

        For example, given the array [2,3,1,2,4,3] and s = 7,
        the subarray [4,3] has the minimal length under the problem constraint.
     */

    /**
     * Solution 1
     * Sliding Window,
     * Time  : O(n),
     * Space : O(1)
     **/
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

    /**
     * Solution 2
     * Binary search with window sum
     * Time : O(nlogn)
     * Space: O(1)
     */
    public int minimumSize(int[] nums, int s) {
        if (nums == null || nums.length == 0) return -1;

        int sum[] = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            sum[i] = sum[i - 1] + nums[i];
        }

        int left = 1, right = nums.length;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            boolean valid = maxWindowSum(sum, mid) >= s;
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

    public int maxWindowSum(int[] sum, int size) {
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = size; i < sum.length; ++i) {
            cur = sum[i] - sum[i - size];
            max = Math.max(cur, max);
        }
        return max;
    }
}
