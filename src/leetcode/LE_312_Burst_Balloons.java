package leetcode;

/**
 * Created by yuank on 5/1/18.
 */
public class LE_312_Burst_Balloons {
    /**
         Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums.
         You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins.
         Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.

         Find the maximum coins you can collect by bursting the balloons wisely.

         Note:
         (1) You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
         (2) 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100

         Example:

         Given [3, 1, 5, 8]

         Return 167

         nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
         coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

         Hard
     */

    /**
         DP
         dp[i][j] : max coin for ballons between i and j.
         Reverse thinking - Assume we get to the last ballon at x between i and j, then => nums[i - 1] * nums[x] * nums[j + 1]
         dp[i][j] = max(dp[i][j], dp[i][x - 1]  + nums[i - 1] * nums[x] * nums[j + 1] + dp[x + 1][j])

         Time  : O(n ^ 3)
         Space : O(n ^ 2)
     **/

    /**
     * Solution 1 : DP with memorization
     * @param nums
     * @return
     */
    public int maxCoins1(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];

        return helper(arr, dp, 1, n);//!!! i = 1, j = n
    }

    //DFS with memorization
    public int helper(int[] arr, int[][] dp, int i, int j) {
        if (i > j) return 0;
        if (dp[i][j] > 0) return dp[i][j];

        //!!! "x <= j"
        for (int x = i;x <= j; x++) {
            dp[i][j] = Math.max(dp[i][j], helper(arr, dp, i, x - 1) + arr[i - 1] * arr[x] * arr[j + 1]
                    + helper(arr, dp, x + 1, j));
        }

        return dp[i][j];
    }

    /**
     * Solution 2 : DP with 3 loops, not recommended for interview.
     */
    public int maxCoins2(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] coins = new int[n + 2];
        int[][] dp = new int[n + 2][n + 2];

        for(int i = 0; i < n; i++) {
            coins[i + 1] = nums[i];
        }
        coins[0] = 1;
        coins[n + 1] = 1;

        for (int l = 1; l <= n; l++) { //length
            for (int i = 1; i <= n - l + 1; i++) {//starting point
                int j = i + l - 1; //end point
                for(int k = i; k <= j; k++) {
                    // System.out.println("i="+i+",k="+k+",j="+j+",l="+l);
                    // int v1 = dp[i][k - 1];
                    // int v2 = coins[i - 1] * coins[k] * coins[j + 1];
                    // int v3 = dp[k + 1][j];
                    dp[i][j] = Math.max(dp[i][j], dp[i][k - 1] + coins[i - 1] * coins[k] * coins[j + 1] + dp[k + 1][j]);
                }
            }
        }

        return dp[1][n];
    }
}
