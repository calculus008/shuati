package lintcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_563_Backpack_V {
    /**
         Given n items with size nums[i] which an integer array and all positive numbers.
         An integer target denotes the size of a backpack. Find the number of possible fill the backpack.

         !!!Each item may only be used once

         Example
         Given candidate items [1,2,3,3,7] and target 7,
         (different sequences are counted as one)

         A solution set is:
         [7]
         [1, 3, 3]
         return 2

         Medium
     */

    /**
     *   https://blog.csdn.net/luoshengkim/article/details/76514558

         这道题是Backpack IV那道凑硬币题目的变种，唯一的区别就是现在每种类型的硬币是不可以重复的选择。每个硬币只能出现一次。

         State:
             dp[m][n] 前m种硬币凑成n元的方案数量
         DP Function:
             dp[m][n] = dp[m - 1][n] + dp[m - 1][n - A[m] ];
         Initialize:
             dp[0][0] = 1
         result:
             dp[m][n]

         时间复杂度是O(m * n)
     */
    public int backPackV1(int[] nums, int target) {
        // state dp[m][n]: the number of combinations that first m kinds of items form the target n
        int m = nums.length, n = target;
        int dp[][] = new int[m + 1][n + 1];
        dp[0][0] = 1;
        // dp function
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i - 1]) {
                    /**
                     * !!! since we already execute "dp[i][j] = dp[i - 1][j]",
                     * this line is actually doing :
                     * dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]]
                     *
                     * 0 - 1 knapsack
                     *
                     * dp[i - 1][j] :                用前i-1个硬币凑成j元的方案数量。
                     *                               即不用当前硬币的方案数量。
                     * dp[i - 1][j - nums[i - 1]] ： 用前i-1个硬币凑成j - nums[i - 1]元的方案数量, 也就是，
                     *                               只要加上当前的硬币就能凑成j元的方案数目。
                     *                               即用当前硬币的方案数量。
                     */
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        // result
        return dp[m][n];
    }


    public int backPackV2(int[] nums, int target) {
        int[] f = new int[target + 1];
        f[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = target; j >= nums[i]; j--) {
                f[j] += f[j - nums[i]];
            }
        }

        return f[target];
    }
}
