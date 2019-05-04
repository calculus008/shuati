package Interviews.Linkedin;

public class LE_518_Coin_Change_II {
    /**
     * You are given coins of different denominations and a total amount of money.
     * Write a function to compute the number of combinations that make up that amount.
     * You may assume that you have infinite number of each kind of coin.
     *
     * Example 1:
     * Input: amount = 5, coins = [1, 2, 5]
     * Output: 4
     * Explanation: there are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     *
     * Example 2:
     * Input: amount = 3, coins = [2]
     * Output: 0
     * Explanation: the amount of 3 cannot be made up just with coins of 2.
     *
     * Example 3:
     * Input: amount = 10, coins = [10]
     * Output: 1
     *
     * Note:
     *
     * You can assume that
     *
     * 0 <= amount <= 5000
     * 1 <= coin <= 5000
     * the number of coins is less than 500
     * the answer is guaranteed to fit into signed 32-bit integer
     */

    /**
     * knapsack problem
     * DP
     * dp[i][j] : total number of ways to make total value j by using the first i coins.
     *
     * Init
     * dp[i][0] = 1, for value 0, there's 1 way -> not choosing any coins.
     *
     * Transition
     * dp[i][j] = sum of ways of using ith coins and NOT using ith coins
     * dp[i][j] = dp[i - 1][j] + (j >= coins[i - 1] ? dp[i][j - coins[i - 1]] : 0);
     *
     * Time  : O(n * m)
     * Space : O(n * m)
     */
    class Solution1 {
        public int change(int amount, int[] coins) {
            if (amount == 0) return 1;
            if (null == coins || coins.length == 0) return 0;

            int n = coins.length;
            int[][] dp = new int[n + 1][amount + 1];
            dp[0][0] = 1;

            for (int i = 1; i <= n; i++) {
                dp[i][0] = 1;
                for (int j =1; j <= amount; j++) {
                    /**
                     * dp[i - 1][j] :
                     * number of ways by only using first i - 1 coins -> ith coins is NOT used
                     * j >= coins[i - 1] ? dp[i][j - coins[i - 1]] : 0  :
                     * number of ways by using ith coins, coins[i - 1] is the ith coins.
                     */
                    dp[i][j] = dp[i - 1][j] + (j >= coins[i - 1] ? dp[i][j - coins[i - 1]] : 0);
                }
            }

            return dp[n][amount];
        }
    }

    /**
     * Now we can see that dp[i][j] only rely on dp[i-1][j] and dp[i][j-coins[i]],
     * then we can optimize the space by only using one-dimension array.
     *
     * Time  : O(n * m)
     * Space : O(m)
     *
     * dp[1] += dp[1 - 1]=1
     * dp[2] += dp[2 - 1]=1
     * dp[3] += dp[3 - 1]=1
     * dp[4] += dp[4 - 1]=1
     * dp[5] += dp[5 - 1]=1
     * dp[2] += dp[2 - 2]=2
     * dp[3] += dp[3 - 2]=2
     * dp[4] += dp[4 - 2]=3
     * dp[5] += dp[5 - 2]=3
     * dp[5] += dp[5 - 5]=4
     */
    class Solution2 {
        public int change(int amount, int[] coins) {
            if (amount == 0) return 1;
            if (null == coins || coins.length == 0) return 0;

            int[] dp = new int[amount + 1];
            dp[0] = 1;

            for (int coin : coins) {
                for(int i = coin; i <= amount; i++) {
                    dp[i] += dp[i - coin];
                }
            }

            return dp[amount];
        }
    }

    //compare with LE_322_Coin_Change, which requires to get min number of coins that sum to amount
//    public int coinChange(int[] coins, int amount) {
//        if (amount == 0) return 0;
//        if (coins == null || coins.length == 0) return -1;
//
//        int[] dp = new int[amount + 1];
//        Arrays.fill(dp, Integer.MAX_VALUE);
//
//        dp[0] = 0;//!!!
//
//        for (int coin : coins) {
//            for (int i = coin; i <= amount; i++) {
//                if (dp[i - coin] != Integer.MAX_VALUE) {
//                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
//                }
//            }
//        }
//
//        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
//    }
}
