package Interviews;

import java.util.Scanner;

public class Number_Of_Ways_Reaching_A_Score {
    /**
     * Consider a game where a player can score 3 or 5 or 10 points in a move.
     * Given a total score n, find number of ways to reach the given score.
     *
     * Examples:
     *
     * Input: n = 20
     * Output: 4
     * There are following 4 ways to reach 20
     * (10, 10)
     * (5, 5, 10)
     * (5, 5, 5, 5)
     * (3, 3, 3, 3, 3, 5)
     *
     * Input: n = 13
     * Output: 2
     * There are following 2 ways to reach 13
     * (3, 5, 5)
     * (3, 10)
     */

    /**
     * https://www.geeksforgeeks.org/count-number-ways-reach-given-score-game/
     *
     * Similar to LE_322_Coin_Change
     */

    public class ScoreCombinations {

        // Function to find number of ways to reach a given score n

        /**
         * Approach:
         * Dynamic Programming Array: Create an array dp[] where dp[i] will store the number of ways to
         * reach the score i.
         *
         * Initialization:
         * 1. Initialize dp[0] to 1 because there is exactly one way to reach a score of 0 (doing nothing).
         * 2. Transition: For each score, check how many ways we can reach it by adding 3, 5, or 10.
         *    The transition can be written as:
         * dp[i] += dp[i - 3] (if i >= 3)
         * dp[i] += dp[i - 5] (if i >= 5)
         * dp[i] += dp[i - 10] (if i >= 10)
         * This way, we accumulate the number of ways to reach a score by considering all possible previous scores.
         */
        public int countWaysToReachScore(int n) {
            // Create an array to store results for each score
            int[] dp = new int[n + 1];

            // Base case: There's one way to reach score 0 (by doing nothing)
            dp[0] = 1;

            // Fill dp[] for all scores from 3 to n
            for (int i = 3; i <= n; i++) {
                dp[i] += dp[i - 3];
            }

            // Fill dp[] for all scores from 5 to n
            for (int i = 5; i <= n; i++) {
                dp[i] += dp[i - 5];
            }

            // Fill dp[] for all scores from 10 to n
            for (int i = 10; i <= n; i++) {
                dp[i] += dp[i - 10];
            }

            // dp[n] contains the number of ways to reach score n
            return dp[n];
        }

//        public static void main(String[] args) {
//            Scanner scanner = new Scanner(System.in);
//
//            // Input the score
//            System.out.print("Enter the score: ");
//            int n = scanner.nextInt();
//
//            // Find and display the number of ways to reach the score
//            System.out.println("Number of ways to reach score " + n + ": " + countWaysToReachScore(n));
//        }
    }
}
