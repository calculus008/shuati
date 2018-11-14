package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 11/13/18.
 */
public class LE_877_Stone_Game {
    /**
         Alex and Lee play a game with piles of stones.  There are an even number of piles arranged
         in a row, and each pile has a positive integer number of stones piles[i].

         The objective of the game is to end with the most stones.  The total number of stones is
         odd, so there are no ties.

         Alex and Lee take turns, with Alex starting first.  Each turn, a player takes the entire
         pile of stones from either the beginning or the end of the row.  This continues until
         there are no more piles left, at which point the person with the most stones wins.

         Assuming Alex and Lee play optimally, return True if and only if Alex wins the game.

         Example 1:

         Input: [5,3,4,5]
         Output: true

         Explanation:
         Alex starts first, and can only take the first 5 or the last 5.
         Say he takes the first 5, so that the row becomes [3, 4, 5].
         If Lee takes 3, then the board is [4, 5], and Alex takes 5 to win with 10 points.
         If Lee takes the last 5, then the board is [3, 4], and Alex takes 4 to win with 9 points.
         This demonstrated that taking the first 5 was a winning move for Alex, so we return true.


         Note:

         2 <= piles.length <= 500
         piles.length is even.
         1 <= piles[i] <= 500
         sum(piles) is odd.

         The same as Leetocde 486. Predict the Winner. Only difference is that 486 expects player1
         to be the winner for corner case {0}. Just change "dp[0] >= 0" in Solution 3 can satisfy it.

         Medium
     */

    /**
     * Solution 1
     * Min-Max + Memoization
     *
     * Time and Space : O(n ^ 2)
     *
     * https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-877-stone-game/
     */
    class Solution1 {
        public boolean stoneGame(int[] piles) {
            int n = piles.length;
            int[][] mem = new int[n][n];
            for (int[] m : mem) {
                Arrays.fill(m, Integer.MIN_VALUE);
            }
            return helper(piles, mem, 0, n - 1) > 0;
        }

        private int helper(int[] piles, int[][] mem, int i, int j) {
            if (i == j) {
                return piles[i];
            }

            if (mem[i][j] == Integer.MIN_VALUE) {
                mem[i][j] = Math.max(helper(piles, mem, i + 1, j),
                        helper(piles, mem, i, j - 1));//!!! "j - 1"
            }

            return mem[i][j];
        }
    }

    /**
     * Solution 2
     * DP
     *
     * Time and Space : O(n ^ 2)
     *
     * dp[i][j] : best RELATIVE score for element from index i to j.
     *
     * Relative score : with the current player's choice - next player's best choice
     *                  for the next round.
     */
    class Solution2 {
        public boolean stoneGame(int[] piles) {
            int n = piles.length;
            int[][] dp = new int[n][n];

            for (int i = 0; i < n; i++) {
                dp[i][i] = piles[i];
            }

            for (int l = 2; l <= n; l++) {
                for (int i = 0; i + l - 1 < n; i++) {
                    int j = i + l - 1;
                    dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
                }
            }

            return dp[0][n - 1] > 0;
        }
    }

    /**
     * Solution 3
     * DP, optimized from Solution 2, only use O(n) space
     * Time  : O(n ^ 2)
     * Space : O(n)
     *
     * dp[i] : max relative score for element from index i to j
     */
    class Solution3 {
        public boolean stoneGame(int[] piles) {
            int n = piles.length;
            int[] dp = new int[n];

            for (int l = 2; l <= n; l++) {
                for (int i = 0; i + l - 1 < n; i++) {
                    int j = i + l - 1;
                    dp[i] = Math.max(piles[i] - dp[i + 1], piles[j] - dp[i]);
                }
            }

            return dp[0] > 0;
        }
    }
}
