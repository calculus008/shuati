package lintcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_440_Backpack_III {
    /**
         Given n kind of items with size Ai and value Vi
         (each item has an infinite number available)
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
     * 2D version
     */
    public int backPackIII1(int[] A, int[] V, int m) {
        int n = A.length;
        int[][] f = new int[n + 1][m + 1];
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                f[i][j] = f[i - 1][j];

                if (j >= A[i - 1]) {
                    f[i][j] = Math.max(f[i][j - A[i - 1]] + V[i - 1], f[i][j]);
                }
            }
        }
        return f[n][m];
    }

    /**
     * 1D version
     */
    public int backPackIII(int[] A, int[] V, int m) {
        // Write your code here
        int n = A.length;
        int[] f = new int[m+1];
        for (int i = 0; i < n; ++i)
            for (int j = A[i]; j <= m; ++j)
                if (f[j - A[i]] + V[i] > f[j])
                    f[j] = f[j - A[i]] + V[i];
        return f[m];
    }
}
