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
     * Prefix Sum + HashTable
     *
     * Time and Space : O(n)
     */

    public int subarraySum(int[] nums, int k) {
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
}
