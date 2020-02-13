package src.leetcode;

public class LE_1043_Partition_Array_For_Maximum_Sum {
    /**
     * Given an integer array A, you partition the array into (contiguous) subarrays of
     * length at most K.  After partitioning, each subarray has their values changed to
     * become the maximum value of that subarray.
     *
     * Return the largest sum of the given array after partitioning.
     *
     * Example 1:
     *
     * Input: A = [1,15,7,9,2,5,10], K = 3
     * Output: 84
     * Explanation: A becomes [15,15,15,9,10,10,10]
     *
     *
     * Note:
     *
     * 1 <= K <= A.length <= 500
     * 0 <= A[i] <= 10^6
     */

    /**
     * https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-1043-partition-array-for-maximum-sum/
     *
     * Time  : O(n * K)
     * Space : O(n)
     *
     * DP分割型问题 (partition)，类似于：
     * LE_139_Word_Break
     * LE_312_Burst_Balloons
     *
     * dp[i] : max sum with the given change for the first i elements in A.
     * dp[0] = 0
     * Transition: dp[i] = max(dp[i - k]) + k * max(A[(i - k + 1) - 1] ~ A[i - 1]), k is between [1...min(i, k)]
     * Answer : dp[n]
     *
     * Example:
     *
     * A = [2, 1, 4, 3], K = 3
     *
     * dp[0] = 0
     * dp[1] = max(dp[0] + 2 * 1) -> 2
     * dp[2] = max(dp[0] + 2 * 1,
     *             dp[1] + 1 * 1) -> 4
     * dp[3] = max(dp[0] + 4 * 3,
     *             dp[1] + 4 * 2,
     *             dp[2] + 4 * 1) -> 12
     * dp[4] = max(dp[0] + 4 * 4,
     *             dp[1] + 4 * 3,
     *             dp[2] + 4 * 2,
     *             dp[4] + 3 * 1) -> 15
     *
     *
     * Illustrate for dp[4]:
     *
     *   [2,   1,   4,  3]
     * |  |_____________|
     * dp[0]     4 * 4
     *
     *    |    |________|
     *  dp[1]    4 * 3
     *
     *    |____|    |___|
     *     dp[2]    4 * 2
     *
     *    |_________|   |
     *      dp[3]     3 * 1
     */
    public int maxSumAfterPartitioning(int[] A, int K) {
        if (A == null || A.length == 0 || K <= 0) return 0;

        int n = A.length;
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 1; j <= Math.min(i, K); j++) {
                max = Math.max(A[i - j], max);
                dp[i] = Math.max(dp[i], dp[i - j] + max * j);
            }
        }

        return dp[n];
    }
}
