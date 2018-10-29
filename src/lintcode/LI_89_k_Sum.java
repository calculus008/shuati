package lintcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_89_k_Sum {
    /**
         Given n distinct positive integers, integer k (k <= n) and a number target.

         Find k numbers where sum is target. Calculate how many solutions there are?

         Example
         Given [1,2,3,4], k = 2, target = 5.

         There are 2 solutions: [1,4] and [2,3].

         Return 2.

         Hard
     */

    /**
     * 3D DP
     *
     * If we change questions to "Pick (any number of) elements from n distinct positive numbers and
     * its sum is target, find how many ways", it will be a normal 0-1 knapsack problem - LI_562_Backpack_IV.
     *
     * Since we must pick k numbers, we have to add another dimension to track number of items picked.
     *
     * dp[i][j][t] : From the first i items, pick j of them and the sum of those j items is t.
     *
     *
         state:
             f[i][j][t]前 i 个数中取 j 个数出来组成和为 t 的组合数目
         function: 
             f[i][j][t] = f[i - 1][j][t] + f[i - 1][j - 1][t - a[i - 1]] (不包括第i 个数的时候组成t的情况 + 包括第i个数的时候组成t的情况)
         initialize:
             f[i][0][0] = 1
         result:
             f[n][k][target]

         时间复杂度：O(n * k * target)
     *
     */
    public class Solution1 {
        public int  kSum(int A[], int k, int target) {
            int n = A.length;
            int[][][] f = new int[n + 1][k + 1][target + 1];
            for (int i = 0; i < n + 1; i++) {
                f[i][0][0] = 1;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= k && j <= i; j++) {
                    for (int t = 1; t <= target; t++) {
                        f[i][j][t] = 0;
                        if (t >= A[i - 1]) {
                            f[i][j][t] = f[i - 1][j - 1][t - A[i - 1]];
                        }
                        f[i][j][t] += f[i - 1][j][t];
                    }
                }
            }
            return f[n][k][target];
        }
    }

    /**
         滚动数组优化空间复杂度。
         prev[j][x]表示从A[0]到A[i-1]，取j个数，使这些数的和为x。
         current[j][x]表示从A[0]到A[i]，取j个数，使这些数的和为x。

         在第i个位置，current[j][x]考虑取A[i]的情况和不取A[i]的情况：
         取A[i]：prev[j-1][x-A[i]] ：前i-1个数取j-1个，和为x-A[i]，加上这次取A[i]，一共取了j个和为x
         不取A[i]：prev[j][x]：前i-1个数取j个，和为x

         所以有：current[j][x] = prev[j-1][x-A[i]]+prev[j][x]
     */
    public class Solution2 {
        public int kSum(int[] A, int k, int target) {
            int[][] prev = new int[k+1][target+1];
            prev[0][0] = 1;
            for(int i = 0; i < A.length; i++) {
                int[][] current = new int[k+1][target+1];
                current[0][0] = 1;
                for(int j = 1; j <= k; j++) {
                    for(int x = 0; x <= target; x++) {
                        current[j][x] = prev[j][x];
                        if(x>=A[i]) {
                            current[j][x] += prev[j-1][x-A[i]];
                        }
                    }
                }
                prev = current;
            }
            return prev[k][target];
        }
    }
}
