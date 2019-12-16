package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 11/28/18.
 */
public class LE_560_Subarray_Sum_Equals_K {
    /**
         Given an array of integers and an integer k,
         you need to find the total number of continuous subarrays whose sum equals to k.

         Example 1:
         Input:nums = [1,1,1], k = 2
         Output: 2

         Note:
         The length of the array is in range [1, 20,000].
         The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/hashtable/leetcode-560-subarray-sum-equals-k/
     *
     * Prefix Sum + HashMap
     *
     * HashMap :
     * key - num
     * number - Frequency of num
     *
     * Optimized for Time (O(n))
     *
     * Time and Space : O(n)
     */

    public int subarraySum1(int[] nums, int k) {
        if (null == nums || nums.length == 0) return 0;

        int result = 0, sum = 0;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            /**
             * a valid subarray starts from index = 0
             */
            if (sum == k) {
                result++;
            }

            if (map.containsKey(sum - k)) {
                /**
                 * !!!
                 * current index i can form multiple subarrays whose
                 * sum is K
                 */
                result += map.get(sum - k);
            }

            //!!!
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return result;
    }

    /**
     * Can be in followup question, how to make it work with constant space.
     *
     * Time  : O(n ^ 2)
     * Space : O(1)
     */
    public int subarraySum2(int[] nums, int k) {
        int count = 0;

        for (int start = 0; start < nums.length; start++) {
            int sum = 0;

            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Prefix sum : use padded array
     *
     * Time  : O(n ^ 2)
     * Space : O(n)
     */
    public int subarraySum3(int[] nums, int k) {
        int count = 0;

        /**
         * Use padded sum array, so we don't need to
         * deal subarray sum starting from index 0 separately
         */
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }

        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                if (sum[end] - sum[start] == k) {
                    count++;
                }
            }
        }

        return count;
    }



}
