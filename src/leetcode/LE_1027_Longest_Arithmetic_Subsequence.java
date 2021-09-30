package leetcode;

import java.util.*;

public class LE_1027_Longest_Arithmetic_Subsequence {
    /**
     * Given an array nums of integers, return the length of the longest arithmetic subsequence in nums.
     *
     * Recall that a subsequence of an array nums is a list nums[i1], nums[i2], ..., nums[ik] with 0 <= i1 < i2
     * < ... < ik <= nums.length - 1, and that a sequence seq is arithmetic if seq[i+1] - seq[i] are all the same
     * value (for 0 <= i < seq.length - 1).
     *
     * Example 1:
     * Input: nums = [3,6,9,12]
     * Output: 4
     * Explanation:
     * The whole array is an arithmetic sequence with steps of length = 3.
     *
     * Example 2:
     * Input: nums = [9,4,7,2,10]
     * Output: 3
     * Explanation:
     * The longest arithmetic subsequence is [4,7,10].
     *
     * Example 3:
     * Input: nums = [20,1,15,3,10,5,8]
     * Output: 4
     * Explanation:
     * The longest arithmetic subsequence is [20,15,10,5].
     *
     * Constraints:
     * 2 <= nums.length <= 1000
     * 0 <= nums[i] <= 500
     *
     * Medium
     *
     * https://leetcode.com/problems/longest-arithmetic-subsequence/
     */

    /**
     * DP
     *
     * Same type problem:
     * LE_446_Arithmetic_Slices_II_Subsequence
     *
     * https://github.com/wisdompeak/LeetCode/tree/master/Dynamic_Programming/1027.Longest-Arithmetic-Sequence
     *
     * Time  : O(n ^ 2)
     * Space : O(n ^ 2)
     */
    class Solution {
        public int longestArithSeqLength(int[] A) {
            int res = Integer.MIN_VALUE;
            int n = A.length;

            /**
             * dp[i][d] : 以A[i]为结尾，以d为共差的等差数列的长度。
             */
            Map<Integer, Integer>[] dp = new Map[n];

            for (int i = 0; i < n; i++) {
                dp[i] = new HashMap<>();

                for (int j = 0; j < i; j++) {
                    long diff = (long)A[i] - A[j];
                    if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) continue;

                    int d = (int)diff;

                    /**
                     * !!!
                     * "dp[j].getOrDefault(d, 1)"
                     * 因为是长度，default value should be 1, not 0.
                     */
                    int c = dp[j].getOrDefault(d, 1);

                    int count = c + 1;
                    dp[i].put(d, count);

                    res = Math.max(res, count);
                }
            }

            return res;
        }
    }
}
