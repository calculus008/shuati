package leetcode;

/**
 * Created by yuank on 2/5/18.
 */
/*
    Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons.
    If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. Here left and right are adjacent indices of i.
    After the burst, the left and right then becomes adjacent.

    Find the maximum coins you can collect by bursting the balloons wisely.
*/

public class LE_312_BurstBallon {
    public static int maxCoins(int[] nums) {
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

        return helper(1, n, coins, dp);
    }

    private static int helper(int i, int j, int[] coins, int[][] dp) {
        System.out.println("  i = " + i + ", j = " + j) ;
        if (i > j) {
            System.out.println ("  i > j, return 0");
            return 0;
        }
        if (dp[i][j] > 0) {
            System.out.println ("  dp[i][j] in cache, return dp["+i+"]["+j+"]=" + dp[i][j]);
            return dp[i][j];
        }


        for (int x = i; x <= j; x++) {
            System.out.println ("   x=" + x + ", val=" + coins[i - 1] * coins[x] * coins[j + 1]);

            dp[i][j] = Math.max(dp[i][j], helper(i, x - 1, coins, dp)
                    + coins[i - 1] * coins[x] * coins[j + 1]
                    + helper(x + 1, j, coins, dp));
        }

        return dp[i][j];
    }

    public static void main(String [] args)
    {
        int[] nums = {3, 1, 5, 8};
        maxCoins(nums);
    }

}
