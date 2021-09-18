package leetcode;

public class LE_1937_Maximum_Number_Of_Points_with_Cost {
    /**
     * You are given an m x n integer matrix points (0-indexed). Starting with 0 points, you want to maximize the number
     * of points you can get from the matrix.
     *
     * To gain points, you must pick one cell in each row. Picking the cell at coordinates (r, c) will add points[r][c]
     * to your score.
     *
     * However, you will lose points if you pick a cell too far from the cell that you picked in the previous row. For
     * every two adjacent rows r and r + 1 (where 0 <= r < m - 1), picking cells at coordinates (r, c1) and (r + 1, c2)
     * will subtract abs(c1 - c2) from your score.
     *
     * Return the maximum number of points you can achieve.
     *
     * abs(x) is defined as:
     *
     * x for x >= 0.
     * -x for x < 0.
     *
     * Example 1:
     * Input: points = [[1,2,3],[1,5,1],[3,1,1]]
     * Output: 9
     * Explanation:
     * The blue cells denote the optimal cells to pick, which have coordinates (0, 2), (1, 1), and (2, 0).
     * You add 3 + 5 + 3 = 11 to your score.
     * However, you must subtract abs(2 - 1) + abs(1 - 0) = 2 from your score.
     * Your final score is 11 - 2 = 9.
     *
     * Example 2:
     * Input: points = [[1,5],[2,3],[4,2]]
     * Output: 11
     * Explanation:
     * The blue cells denote the optimal cells to pick, which have coordinates (0, 1), (1, 1), and (2, 0).
     * You add 5 + 3 + 4 = 12 to your score.
     * However, you must subtract abs(1 - 1) + abs(1 - 0) = 1 from your score.
     * Your final score is 12 - 1 = 11.
     *
     * Constraints:
     * m == points.length
     * n == points[r].length
     * 1 <= m, n <= 105
     * 1 <= m * n <= 105
     * 0 <= points[r][c] <= 105
     *
     * Medium
     *
     * https://leetcode.com/problems/maximum-number-of-points-with-cost/
     */

    /**
     * DP
     *
     * https://github.com/wisdompeak/LeetCode/tree/master/Dynamic_Programming/1937.Maximum-Number-of-Points-with-Cost
     *
     * 我们很容易写出这样的DP写法：
     *
     * for (int i=0; i<m; i++)
     *   for (int j=0; j<n; j++)
     *     for (int k=0; k<n; k++)
     *       dp[i][j] = max(dp[i][j], dp[i-1][k] - abs(j-k) + points[i][j]);
     * 这样的时间复杂度是o(MNN)，显然会TLE。怎么改进呢？
     *
     * 我们将绝对值符号拆开就会发现
     *
     * dp[i][j] = max{ dp[i-1][k] + k - j  + points[i][j]};    for k<=j
     * dp[i][j] = max{ dp[i-1][k] - k + j  + points[i][j]};    for k>=j
     * 我们将dp[i-1][k]+k看做是一个序列，那么dp[i][j]就需要在这个序列的前j个里面挑一个最大的。于是dp[i][j]其实就是这个序列的rolling
     * max value，再加上一个常数项，计算量可以均摊成o(1)。
     *
     * 同理，我们将将dp[i-1][k]-k看做是一个序列，那么dp[i][j]就需要在这个序列的后面n-j个里面挑一个最大的。于是dp[i][j]也是这个序列的
     * rolling max value再加上一个常数项，计算量可以均摊成o(1)。
     *
     * 注意，dp[i][j]最终是需要在两段区间（k<=j 和 k>=j）各自的最大值中挑选一个更大的。
     *
     * Time : O(m * n)
     * Space : O(m * n), can be reduced to O(n) using rolling array.
     *
     * Similar problem:
     * https://leetcode.com/problems/best-sightseeing-pair/discuss/260850/JavaC%2B%2BPython-One-Pass-O(1)-space
     */
    class Solution {
        public long maxPoints(int[][] points) {
            int m = points.length;
            int n = points[0].length;


            //dp[i][j] : max points by picking ith row jth col.
            long[][] dp = new long[m][n];

            for (int i = 0; i < n; i++) {
                dp[0][i] = points[0][i];
            }

            for (int i = 1; i < m; i++) {
                long max = Long.MIN_VALUE;
                for (int j = 0; j < n; j++) {
                    max = Math.max(max, dp[i - 1][j] + j);
                    dp[i][j] = Math.max(dp[i][j], max + points[i][j] - j);
                }

                max = Long.MIN_VALUE;
                for (int j = n - 1; j >= 0; j--) {
                    max = Math.max(max, dp[i - 1][j] - j);
                    dp[i][j] = Math.max(dp[i][j], max + points[i][j] + j);
                }
            }

            long res = Long.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                res = Math.max(res, dp[m - 1][i]);
            }

            return res;
        }
    }
}
