package leetcode;

/**
 * Created by yuank on 11/17/18.
 */
public class LE_813_Largest_Sum_Of_Averages {
    /**
         We partition a row of numbers A into at most K adjacent (non-empty) groups,
         then our score is the sum of the average of each group.
         What is the largest score we can achieve?

         Note that our partition must use every number in A,
         and that scores are not necessarily integers.

         Example:
         Input:
         A = [9,1,2,3,9]
         K = 3
         Output: 20

         Explanation:
         The best choice is to partition A into [9], [1, 2, 3], [9].
         The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.

         We could have also partitioned A into [9, 1], [2], [3, 9], for example.
         That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.


         Note:

         1 <= A.length <= 100.
         1 <= A[i] <= 10000.
         1 <= K <= A.length.
         Answers within 10^-6 of the correct answer will be accepted as correct.

         Medium
     */

    /**
     *   DP
     *   Time  : O(K * n ^ 2)
     *   Space : O(K * n) (can be optimized to O(n) with rolling arrays)
     *
         dp[k][i] : largest sum of average with the first i elements divided into k groups

         找分割点j
         do[k][i] = max(dp[k - 1][j] + Avg((j+1)th to ith element), dp[k][j])
         In other words, the first j elements in k - 1 groups, the first j + 1 th to the first ith in one group

         init:
         dp[1][i] = Avg(the first i elements)
     **/
    public double largestSumOfAverages(int[] A, int K) {
        int n = A.length;

        /**
         sums[] and dp[][] all use number of elements, NOT real index
         !!!
         sums[i] : sum of the first i elements
         **/
        double[][] dp = new double[K + 1][n + 1];
        double[] sums = new double[n + 1];

        for (int i = 1; i <= n; i++) {
            sums[i] = sums[i - 1] + A[i - 1];
            dp[1][i] = sums[i] / i;
        }

        /**
         * loop number of groups, since 1 (one group) is already taken care of
         * in the for loop above, k starts with 2.
         */
        for (int k = 2; k <= K; k++) {
            /**
             * loop i - number of elements, for k groups, there should be at least k elements
             * so i starts with k
             */
            for (int i = k; i <= n; i++) {
                /**
                 * loop cut points, j is NOT index, it's the first j elements.
                 * The first j elements should be in k - 1 groups.
                 * Therefore j should start from k - 1, since for k - 1 groups,
                 * there should be at least k - 1 elements.
                 *
                 * "j < i": the last group should have at least one element.
                 */
                for (int j = k - 1; j < i; j++) {
                    /**
                     * "(sums[i] - sums[j]) / (i - j)"
                     *
                     * It's average for numbers starting from the (j + 1)th element and
                     * ending with ith element.
                     */
                    dp[k][i] = Math.max(dp[k][i],
                            dp[k - 1][j] + (sums[i] - sums[j]) / (i - j));
                }
            }
        }

        return dp[K][n];
    }
}
