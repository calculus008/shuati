package Interviews.Indeed.lc;

import java.util.HashMap;
import java.util.Map;

public class LE_523_Continuous_Subarray_Sum {
    /**
     * Given a list of non-negative numbers and a target integer k, write a function to check
     * if the array has a continuous subarray of size at least 2 that sums up to the multiple
     * of k, that is, sums up to n*k where n is also an integer.
     *
     * Example 1:
     * Input: [23, 2, 4, 6, 7],  k=6
     * Output: True
     * Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
     *
     * Example 2:
     * Input: [23, 2, 6, 4, 7],  k=6
     * Output: True
     * Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
     *
     * Note:
     *
     * The length of the array won't exceed 10,000.
     * You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
     *
     * Medium
     */

    /**
     * We iterate through the input array exactly once, keeping track of the running sum mod k
     * of the elements in the process. If we find that a running sum value at index j has been
     * previously seen before in some earlier index i in the array, then we know that the
     * sub-array (i,j] contains a desired sum.
     *
     * Time  : O(n)
     * Space : O(k)
     *
     * Key Insights:
     * This is one of those magics of remainder theorem :)
     *
     * (a+(n*x))%x is same as (a%x)
     *
     * For e.g. in case of the array [23,2,6,4,7] the running sum is [23,25,31,35,42] and the remainders
     * are [5,1,1,5,0]. We got remainder 5 at index 0 and at index 3. That means, in between these two
     * indexes we must have added a number which is multiple of the k.
     *
     * so many traps here :
     * k can be 0, element of the array can be 0, and also a single element is not allowed
     * ("continuous subarray of size at least 2"), at least two continuous elements ..
     *
     */
    class Solution {
        public boolean checkSubarraySum(int[] nums, int k) {
            if (nums == null || nums.length == 0) return false;

            int n = nums.length;
            int sums = 0;

            Map<Integer, Integer> map = new HashMap<>();
            /**
             * !!!
             * corner case: if the very first subarray with first two numbers in array could form
             * the result, we need to put mod value 0 and index -1 to make it as a true case.
             *
             * Example:
             * [1, 2, 3, 4], k = 3
             * a[0] + a[1] = 3
             * map : 1 -> 0
             *       0 -> 1
             *       0 -> -1
             *
             * [0, 0], k = 0
             * map : 0 -> -1
             *       0 -> 0, 0 - (-1) = 1, not the answer
             *       0 -> 1, 1 - (-1) = 2, it's the answer
             *
             */
            map.put(0, -1);

            for (int i = 0; i < n; i++) {
                sums += nums[i];

                /**
                 * !!!
                 * For case that k is 0, we don't need to do mod,
                 * just keep sums as prefix sum, whenever we find
                 * two prefix sum has the same value, then there's
                 * subarray which has sum value as 0.
                 */
                if (k != 0) {
                    sums %= k;
                }

                if (map.containsKey(sums)) {
                    /**
                     * !!!
                     * "continuous subarray of size at least 2"
                     */
                    if (i - map.get(sums) > 1) {
                        return true;
                    }
                } else {
                    map.put(sums, i);
                }
            }

            return false;
        }
    }
}
