package lintcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_440_Backpack_III {
    /**
         Given n kind of items with size Ai and value Vi
         (each item has an INFINITE number available)
         and a backpack with size m.

         What's the maximum value can you put into the backpack?

         Example
         Given 4 items with size [2, 3, 5, 7] and value [1, 5, 2, 4],
         and a backpack with size 10.
         The maximum value is 15.

         Notice
         You cannot divide item into small pieces and the total size
         of items you choose should smaller or equal to m.

         Hard

         无限背包问题 (unbounded knapsack)
     */

    /**
     这道题应该是背包问题里面比较难的题目了。我们应该注意如果能固定取几次，我们要把物品取的是哪一个记录到数组里面，
     但是如果可以取任意多次，我们就没必要记录了。但是所有背包问题的状态函数都必须与总承重有关，这一点是不会变的。
     */

    /**
     * Solution 1
     * 2D version
     */
    public int backPackIII1(int[] A, int[] V, int m) {
        if (m == 0 || A == null || A.length == 0 || V == null || V.length == 0) return 0;

        int n = A.length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                dp[i][j] = dp[i - 1][j];
                if (j >= A[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j - A[i - 1]] + V[i - 1], dp[i][j]);
                }
            }
        }

        return dp[n][m];
    }

    /**
     * Solution 2
     *
     * 这道题是上题Backpack II的变种，区别就每种item可以重复的选择，基本思路跟上题一样，只不过由于可以重复，
     * 所以在最内层循环还要再加一层while循环用于遍历枚举重复的组合。
     *
     * Solution 1 seems to be an optimized version of Solution 2
     *
     * Time : O(m * n * k)。
     */
    public int backPackIII2(int[] A, int[] V, int m) {
        int n = A.length;
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                int k = 0;
                /**
                 * this while loop approach seems to be the common
                 * technique to deal with unbounded knapsack problem
                 */
                while (A[i - 1] * k <= j) {
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j-A[i-1]*k]+V[i-1]*k);
                    k++;
                }
            }
        }

        return dp[n][m];
    }


    /**
     * Solution 3
     * 1D version
     */
    public int backPackIII3(int[] A, int[] V, int m) {
        int n = A.length;
        int[] f = new int[m+1];
        for (int i = 0; i < n; ++i)
            for (int j = A[i]; j <= m; ++j)
                if (f[j - A[i]] + V[i] > f[j])
                    f[j] = f[j - A[i]] + V[i];
        return f[m];
    }
}
