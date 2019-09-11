package leetcode;

public class LE_486_Predict_The_Winner {
    /**
     * Given an array of scores that are non-negative integers.
     * Player 1 picks one of the numbers from either end of the array
     * followed by the player 2 and then player 1 and so on.
     * Each time a player picks a number, that number will not be available
     * for the next player. This continues until all the scores have been
     * chosen. The player with the maximum score wins.
     *
     * Given an array of scores, predict whether player 1 is the winner.
     * You can assume each player plays to maximize his score.
     *
     * Example 1:
     * Input: [1, 5, 2]
     * Output: False
     * Explanation: Initially, player 1 can choose between 1 and 2.
     * If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5.
     * If player 2 chooses 5, then player 1 will be left with 1 (or 2).
     * So, final score of player 1 is 1 + 2 = 3, and player 2 is 5.
     * Hence, player 1 will never be the winner and you need to return False.
     *
     * Example 2:
     * Input: [1, 5, 233, 7]
     * Output: True
     * Explanation: Player 1 first chooses 1. Then player 2 have to choose between 5 and 7.
     * No matter which number player 2 choose, player 1 can choose 233.
     * Finally, player 1 has more score (234) than player 2 (12),
     * so you need to return True representing player1 can win.
     *
     * Note:
     * 1 <= length of the array <= 20.
     * Any scores in the given array are non-negative integers and will not exceed 10,000,000.
     * If the scores of both players are equal, then player 1 is still the winner.
     *
     * Same Problem : LE_877_Stone_Game
     */


    /**
     * http://zxi.mytechroad.com/blog/leetcode/leetcode-486-predict-the-winner/
     *
     * Min_Max
     *
     * Brutal Force :
     * Time  : O(2 ^ n)
     * Space : O(n)
     */
    class Solution1 {
        public boolean PredictTheWinner(int[] nums) {
            return getScore(nums, 0, nums.length - 1) >= 0;
        }

        /**
         * max score diff fro player in current round against the other player
         */
        private int getScore(int[] nums, int l, int r) {
            if (l == r) {
                return nums[l];
            }

            return Math.max(nums[l] - getScore(nums, l + 1, r), nums[r] - getScore(nums, l, r - 1));
        }
    }

    /**
     * DP
     *
     * dp[i][j] : Max score for sub array between index i and j
     *
     * Time  : O(n ^ 2), we have n ^ 2 sub-problems
     * Space : O(n)
     */
    class Solution2 {
        public boolean PredictTheWinner(int[] nums) {
            int n = nums.length;
            int[] dp = new int[n];

            for (int l = 2; l <= n; l++) {
                for (int i = 0; i + l - 1 < n; i++) {
                    int j = i + l - 1;
                    dp[i] = Math.max(nums[i] - dp[i + 1], nums[j] - dp[i]);
                }
            }

            /**
             * !!!
             * >= 0, because "If the scores of both players are equal, then player 1 is still the winner"
             */
            return dp[0] >= 0;
        }
    }
}
