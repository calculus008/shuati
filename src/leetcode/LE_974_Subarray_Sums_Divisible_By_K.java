package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_974_Subarray_Sums_Divisible_By_K {
    /**
     * Given an array A of integers, return the number of (contiguous, non-empty) subarrays that have a sum divisible by K.
     *
     * Example 1:
     *
     * Input: A = [4,5,0,-2,-3,1], K = 5
     * Output: 7
     * Explanation: There are 7 subarrays with a sum divisible by K = 5:
     * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
     *
     *
     * Note:
     * 1 <= A.length <= 30000
     * -10000 <= A[i] <= 10000
     * 2 <= K <= 10000 (!!!, no need to worry about K = 1 or K = 0)
     *
     * Medium
     */

    /**
     * Almost the same as LE_523_Continuous_Subarray_Sum, the difference is that we may have negative number in
     * the given array.
     *
     * Important insight:
     * if sum[0, i] % K == sum[0, j] % K, sum[i + 1, j] is divisible by by K !!!
     *
     * Example
     * [4,5,0,-2,-3,1], K = 5
     *
     * [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3], [4, 5, 0, -2, -3, 1],
     *
     * prefix sum:
     * 4,9,9,7,4,5
     *
     * prefix sum / K:
     * 4, 4, 4, 2, 4, 0
     * |__|
     * |_____|
     *    |__|
     *       |_____|
     *    |________|
     * |___________|
     * |______________|
     *
     * So every pair of the same value from presum/K forms a subarray meets requirement.（!!!)
     *
     * count add 0
     * 4 -> 1
     * count add 1
     * 4 -> 2
     * count add 2
     * 4 -> 3
     * count add 0
     * 2 -> 1
     * count add 3
     * 4 -> 4
     * count add 1
     * 0 -> 2
     *
     */
    class Solution {
        public int subarraysDivByK(int[] A, int K) {
            Map<Integer, Integer> map = new HashMap<>();
            /**
             * !!!
             */
            map.put(0, 1);

            int sum = 0;
            int count = 0;

            for (int a : A) {
                sum = (sum + a) % K;

                /**
                 * !!!
                 */
                if (sum < 0) {
                    sum += K;
                }

                count += map.getOrDefault(sum, 0);
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }

            return count;
        }
    }
}
