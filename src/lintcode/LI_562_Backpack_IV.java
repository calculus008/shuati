package lintcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_562_Backpack_IV {
    /**
         Given n items with size nums[i] which an integer array and all positive numbers,
         no duplicates. An integer target denotes the size of a backpack.
         Find the number of possible fill the backpack.

         !!!(It means fill the complete target)

         Each item may be chosen unlimited number of times
         Difference sequences of the same set of items are counted as 1

         If different sequence is counted as different way, see :
         LE_377_Combination_Sum_IV

         Example
         Given candidate items [2,3,6,7] and target 7,

         A solution set is:
         [7]
         [2, 2, 3]
         return 2

         [2, 2, 2] only gets to 6, not 7, therefore it's not a solution.

         Medium
     */

    /**
     *   https://blog.csdn.net/luoshengkim/article/details/76514558
         State:
             dp[m][n] 前m种硬币凑成n元的方案数量
         DP Function:
             dp[m][n] = dp[m - 1][n] + dp[m - 1][n - A[m] * 1] + dp[m - 1][n - A[m] * 2] + dp[m - 1][n - A[m] * 3] + ...
         Initialize:
             dp[0][0] = 1
         result:
             dp[m][n]

         时间复杂度是O(m * n * k)
     */
    public static int backPackIV1(int[] A, int m) {
        if (m == 0 || A == null || A.length == 0) return 0;

        int n = A.length;
        int[][] dp = new int[n + 1][m + 1];

        dp[0][0] = 1;//!!!

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                System.out.println("i = " + i + ", j = " + j);
                int k = 0;
                while (j >= A[i - 1] * k) {
                    System.out.println("    j=" +j + " > " + "A[" + i + "-1] * " + k);

                    dp[i][j] += dp[i - 1][j - A[i - 1] * k];
                    k++;

                    System.out.println("    dp["+i+"]["+j+"]=" + dp[i][j]);
                }
            }
        }

        return dp[n][m];
    }

    public int backPackIV2(int[] nums, int target) {
        int[] f = new int[target + 1];
        f[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            for (int j = nums[i]; j <= target; j++) {
                f[j] += f[j - nums[i]];
            }
        }

        return f[target];
    }

    public static void main(String [] args) {
        backPackIV1(new int[] {2, 3, 6, 7}, 7);
    }
}

