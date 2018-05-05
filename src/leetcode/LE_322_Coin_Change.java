package leetcode;

/**
 * Created by yuank on 5/4/18.
 */
public class LE_322_Coin_Change {
    /**
         You are given coins of different denominations and a total amount of money amount.
         Write a function to compute the fewest number of coins that you need to make up that amount.
         If that amount of money cannot be made up by any combination of the coins, return -1.

         Example 1:
         coins = [1, 2, 5], amount = 11
         return 3 (11 = 5 + 5 + 1)

         Example 2:
         coins = [2], amount = 3
         return -1.

         Note:
         You may assume that you have an infinite number of each kind of coin.

         Medium
     */

    /**
     *   DP
         Time : O(n * amount)
         Space : O(amount)
     */
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (coins == null || coins.length == 0) return -1;

        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                //!!!
                if (coins[j] <= i && dp[i - coins[j]] != -1) {
                    min = Math.min(min, dp[i - coins[j]] + 1);
                }
            }
            //!!!"dp[i] ="
            dp[i] = min == Integer.MAX_VALUE ? -1 : min;
        }

        return dp[amount];
    }
}
