package src.leetcode;

import java.util.Arrays;

public class LE_931_Minimum_Falling_Path_Sum {
    /**
     * Given a square array of integers A, we want the minimum sum of a falling path through A.
     *
     * A falling path starts at any element in the first row, and chooses one element from each
     * row.  The next row's choice must be in a column that is different from the previous row's
     * column by at most one.
     *
     * Example 1:
     *
     * Input: [[1,2,3],[4,5,6],[7,8,9]]
     * Output: 12
     * Explanation:
     * The possible falling paths are:
     * [1,4,7], [1,4,8], [1,5,7], [1,5,8], [1,5,9]
     * [2,4,7], [2,4,8], [2,5,7], [2,5,8], [2,5,9], [2,6,8], [2,6,9]
     * [3,5,7], [3,5,8], [3,5,9], [3,6,8], [3,6,9]
     * The falling path with the smallest sum is [1,4,7], so the answer is 12.
     *
     * Note:
     *
     * 1 <= A.length == A[0].length <= 100
     * -100 <= A[i][j] <= 100
     *
     * Medium
     *
     * Similar porblem : LE_120_Triangle
     */

    /**
     * DP
     * https://leetcode.com/problems/minimum-falling-path-sum/discuss/186666/C%2B%2BJava-4-lines-DP
     *
     * The minimum path to get to element A[i][j] is the minimum of A[i - 1][j - 1], A[i - 1][j] and A[i - 1][j + 1].
     * Starting from row 1, we add the minimum path to each element. The smallest number in the last row is the minimum
     * path sum.
     * Example:
     * [1, 2, 3]
     * [4, 5, 6] => [5, 6, 8]
     * [7, 8, 9] => [7, 8, 9] => [12, 13, 15]
     *
     * Time  : O(N ^ 2)
     * Space : O(1)
     */
    class Solution1 {
        public int minFallingPathSum(int[][] A) {
            if (null == A || A.length == 0) return 0;

            int m = A.length;

            for (int i = 1; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    A[i][j] += Math.min(A[i - 1][j], Math.min(A[i - 1][Math.max(0, j - 1)], A[i - 1][Math.min(m - 1, j + 1)]));
                }
            }

            return Arrays.stream(A[m - 1]).min().getAsInt();
        }
    }

    /**
     * Using extra space - dp[][]
     */
    class Solution2 {
        public int minFallingPathSum(int[][] A) {
            if (A == null || A.length == 0) return 0;

            int m = A.length;
            int n = A[0].length;

            int[][] dp = new int[m][n];

            /**
             * !!!
             * Must do this!
             */
            for (int i = 0; i < n; i++) {
                dp[0][i] = A[0][i];
            }

            for (int i = 1; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = A[i][j] + Math.min(Math.min(dp[i - 1][Math.max(0, j - 1)], dp[i - 1][Math.min(n - 1, j + 1)]), dp[i - 1][j]);
                }
            }

            return Arrays.stream(dp[m - 1]).min().getAsInt();
        }

    }
}
