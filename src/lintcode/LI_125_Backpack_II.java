package lintcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_125_Backpack_II {
    /**
         Given n items with SIZE Ai and VALUE Vi, and a backpack with size m.
         What's the maximum value can you put into the backpack?

         Example
         Given 4 items with size [2, 3, 5, 7] and value [1, 5, 2, 4],
         and a backpack with size 10. The maximum value is 9.

         Challenge
         O(n x m) memory is acceptable, can you do it in O(m) memory?

         Notice
         You cannot divide item into small pieces and the total size of
         items you choose should smaller or equal to m.

         Medium
     */

    /**
     * https://blog.csdn.net/luoshengkim/article/details/76514558
     *
     * Solution 1
     * Time and Space : O(m * n)
     *
     * bp[i][j] ： 前i个物品，取出一些能放入size为j的空间，这i个物品可能的最大价值。
     */
    public int backPackII1(int m, int[] A, int[] V) {
        if (m == 0 || A == null || A.length == 0 || V == null || V.length == 0) return 0;

        int n = A.length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (j < A[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - A[i - 1]] + V[i - 1]);
                }
            }
        }

        return dp[n][m];
    }

    /**
     * Solution 2
     * Time  : O(m * n)
     * Space : O(m)
     * 滚动数组 ： apply “% 2” everywhere index i is referenced.
     */
    public int backPackII2(int m, int[] A, int[] V) {
        if (m == 0 || A == null || A.length == 0 || V == null || V.length == 0) return 0;

        int n = A.length;
        int[][] dp = new int[2][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 || j == 0) {
                    dp[i % 2][j] = 0;
                } else if (j < A[i - 1]) {
                    dp[i % 2][j] = dp[(i - 1) % 2][j];
                } else {
                    dp[i % 2][j] = Math.max(dp[(i - 1) % 2][j], dp[(i - 1) % 2][j - A[i - 1]] + V[i - 1]);
                }
            }
        }

        return dp[n % 2][m];
    }

    /**
     * Solution 2
     * Time  : O(m * n)
     * Space : O(m)
     */
    public int backPackII3(int m, int[] A, int[] V) {
        if (m == 0 || A == null || A.length == 0 || V == null || V.length == 0) return 0;

        int n = A.length;
        int[] dp = new int[m + 1];

        for (int i = 0; i < n; i++) {
            for (int j = m; j >= A[i]; j--) {
                if (dp[j] < dp[j - A[i]] + V[i]) {
                    dp[j] = dp[j - A[i]] + V[i];
                }
            }
        }

        return dp[m];
    }

    /**
     * Solution 3
     * Same as Solution 1, use the form of 2D solution for LI_440_Backpack_III
     * The difference is only in transfer equation
     */
    public int backPackII4(int m, int[] A, int[] V) {
        if (m == 0 || A == null || A.length == 0 || V == null || V.length == 0) return 0;

        int n = A.length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= A[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j - A[i - 1]] + V[i - 1], dp[i - 1][j]);
                    /**
                        Since we already execute " dp[i][j] = dp[i - 1][j]", we can also writ this line as :
                        dp[i][j] = Math.max(dp[i - 1][j - A[i - 1]] + V[i - 1], dp[i][j]);
                     **/
                }
            }
        }

        return dp[n][m];
    }
}
