package leetcode;

import java.util.Arrays;

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
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-322-coin-change/
     *
     *   DP
     *   Time  : O(n * amount)
     *   Space : O(amount)
     */
    class Solution1 {
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

    class Solution2 {
        /**
         Time : O(n * amount)
         Space : O(amount)
         */
        public int coinChange(int[] coins, int amount) {
            if (amount == 0) return 0;
            if (coins == null || coins.length == 0) return -1;

            int[] dp = new int[amount + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);

            dp[0] = 0;//!!!

            for (int coin : coins) {
                for (int i = coin; i <= amount; i++) {
                    if (dp[i - coin] != Integer.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                    }
                }
            }

            return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
        }
    }

    /**
     * DFS + Greedy
     * Time  : O(n * amount ^ 2)
     * Space : O(n)
     */
    class Solution3 {
        int res = Integer.MAX_VALUE;

        public int coinChange(int[] coins, int amount) {
            if (amount == 0 || null == coins || coins.length == 0) {
                return 0;
            }

            /**
             * sort, then start from end of the coins, use the largest coin value first
             * since we want to find the min number of coins.
             */
            Arrays.sort(coins);

            helper(coins, amount, 0, coins.length - 1);

            return res == Integer.MAX_VALUE ? -1 : res;
        }

        private void helper(int[] coins, int remain, int numOfCoins, int pos) {
            if (pos == 0) {
                if (remain % coins[pos] == 0) {
                    res = Math.min(res, remain / coins[pos] + numOfCoins);
                }
                return;
            }

            /**
             * !!!
             * 终止条件 : "i >= 0 && numOfCoins + i < res"
             * 一旦当前coin总数大于等于res，stop the loop right away， otherwise TLE
             *
             * !!!
             * 注意参数的传递：
             * "remain - coins[pos] * i"
             * "numOfCoins + i"
             * "pos - 1"
             */
            for (int i = remain / coins[pos]; i >= 0 && numOfCoins + i < res; i--) {
                helper(coins, remain - coins[pos] * i, numOfCoins + i, pos - 1);
            }
        }
    }
}
