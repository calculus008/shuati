package leetcode;

public class LE_718_Maximum_Length_Of_Repeated_Subarray {
    /**
     * Given two integer arrays A and B, return the maximum length of an
     * subarray that appears in both arrays.
     *
     * Example 1:
     *
     * Input:
     * A: [1,2,3,2,1]
     * B: [3,2,1,4,7]
     * Output: 3
     * Explanation:
     * The repeated subarray with maximum length is [3, 2, 1].
     *
     * Note:
     * 1 <= len(A), len(B) <= 1000
     * 0 <= A[i], B[i] < 100
     */

    /**
     * DP
     * dp[i][j] is the length of longest common subarray ending with nums[i-1] and nums[j-1]
     */
    class Solution {
        public int findLength(int[] A, int[] B) {
            if (A == null || B == null) return 0;

            int m = A.length;
            int n = B.length;

            int[][] dp = new int[m + 1][n + 1];

            int res = 0;

            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 0;
                    } else {
                        if (A[i - 1] == B[j - 1]) {
                            dp[i][j] = dp[i - 1][j - 1] + 1;
                            res = Math.max(res, dp[i][j]);
                        }
                    }
                }
            }

            return res;
        }
    }
}
